package com.meli.frescos.service;

import com.meli.frescos.exception.*;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.BatchStockRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all BatchStock related functions
 * Using @Service from spring
 */
@Service
public class BatchStockService implements IBatchStockService {

    private final BatchStockRepository batchStockRepository;

    private final IProductService iProductService;

    private final ISectionService iSectionService;

    private final IOrderProductService iOrderProductService;

    public BatchStockService(BatchStockRepository batchStockRepository, IProductService iProductService, ISectionService iSectionService, IOrderProductService iOrderProductService) {
        this.batchStockRepository = batchStockRepository;
        this.iProductService = iProductService;
        this.iSectionService = iSectionService;
        this.iOrderProductService = iOrderProductService;
    }

    /**
     * Return all BatchStocks
     *
     * @return List of BatchStockModel
     */
    @Override
    public List<BatchStockModel> getAll() {
        return batchStockRepository.findAll();
    }

    /**
     * Return BatchStock given id
     *
     * @param id the batchStockModel id
     * @return BatchStockModel
     * @throws BatchStockByIdNotFoundException when BatchStock not found
     */
    @Override
    public BatchStockModel getById(Long id) throws BatchStockByIdNotFoundException {
        return batchStockRepository.findById(id).orElseThrow(() -> new BatchStockByIdNotFoundException(id));
    }

    /**
     * Create a new BatchStock given model
     * @param batchStock new BatchStock to create
     * @return the BatchStock created
     */
    @Override
    public BatchStockModel save(BatchStockModel batchStock) {
        batchStock.setSection(iSectionService.getById(batchStock.getSection().getId()));
        return batchStockRepository.save(batchStock);
    }

    /**
     * Creates new BatchStock given a list of BatchStock
     * @param batchStockList list of BatchStock to create
     * @return the list BatchStock created
     * @throws Exception when invalid BatchStock
     */
    private List<BatchStockModel> save(List<BatchStockModel> batchStockList) {
        return batchStockRepository.saveAll(batchStockList);
    }

    /**
     * Return a list of BatchStock given a product id
     *
     * @param productId the product id
     * @return a list of BatchStock
     */
    @Override
    public List<BatchStockModel> getByProductId(Long productId) {
        ProductModel product = iProductService.getById(productId);
        return batchStockRepository.findByProduct(product);
    }

    /**
     * Return a list of BatchStock given a section id
     *
     * @param sectionId the section id
     * @return a list of BatchStock
     * @throws Exception
     */
    @Override
    public List<BatchStockModel> getBySectionId(Long sectionId) {
        SectionModel section = iSectionService.getById(sectionId);
        return batchStockRepository.findBySection(section);
    }

    /**
     * Return a list of BatchStock given section id and number of days to a BatchStock due date
     *
     * @param sectionId the section id
     * @param numberOfDays number of days to be added to the current day to arrive at the due date to be sought
     * @return a list of BatchStock
     */
    @Override
    public List<BatchStockModel> getBySectionIdAndDueDate(Long sectionId, Integer numberOfDays) {
        SectionModel section = iSectionService.getById(sectionId);
        return batchStockRepository.findBySectionAndDueDateBetween(section, LocalDate.now(), LocalDate.now().plusDays(numberOfDays));
    }

    /**
     * Return a list of BatchStock given category and number of days to a BatchStock due date
     *
     * @param category the category
     * @param numberOfDays number of days to be added to the current day to arrive at the due date to be sought
     * @return a list of BatchStock
     */
    @Override
    public List<BatchStockModel> getByCategoryAndDueDate(CategoryEnum category, Integer numberOfDays) {
        List<SectionModel> sectionList = iSectionService.getByCategory(category);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        for (SectionModel section : sectionList) {
            batchStockList.addAll(batchStockRepository.findBySectionAndDueDateBetween(section, LocalDate.now(), LocalDate.now().plusDays(numberOfDays)));
        }
        return batchStockList;
    }

    /**
     * Return quantity of product in the BatchStock given product id
     *
     * @param productId the product id
     * @return a list of BatchStock
     */
    @Override
    public Integer getTotalBatchStockQuantity(Long productId) {
        return getByProductId(productId).stream().mapToInt(BatchStockModel::getQuantity).sum();
    }

    /**
     * Return closest due date given product id
     *
     * @param productId the product id
     * @return a due date
     */
    @Override
    public LocalDate getClosestDueDate(Long productId) throws NullDueDateException {
        return getByProductId(productId).stream().min(Comparator.comparing(BatchStockModel::getDueDate)).orElseThrow(() -> new NullDueDateException("Null DueDate on database!")).getDueDate();
    }

    /**
     * Checks if the BatchStock list category is valid for the section
     *
     * @param category the category
     * @param batchStockList the list of BatchStock
     * @throws Exception when the product not allowed in the section
     */
    private void isCategoryPermittedInSections(CategoryEnum category, List<BatchStockModel> batchStockList) throws ProductNotPermittedInSectionException {
        List<Long> notPermitedSections = new ArrayList<>();
        batchStockList.forEach(b -> {
            if(!b.getSection().getCategory().equals(category)) {
                notPermitedSections.add(b.getSection().getId());
            }
        });

        if (!notPermitedSections.isEmpty()) {
            throw new ProductNotPermittedInSectionException("This product is not permited in these sections: " + notPermitedSections);
        }
    }

    /**
     * Checks if product fits in section
     *
     * @param product the product
     * @param inboundBatchStockList the list of BatchStock
     * @throws Exception when section have not enough space
     */
    private void productFitsInSection(ProductModel product, List<BatchStockModel> inboundBatchStockList) throws NotEnoughSpaceInSectionException {
        HashMap<Long, Double> sectionFreeRoomMap = new HashMap<>();
        HashMap<Long, Double> inboundTotalVolumeMap = new HashMap<>();
        List<SectionModel> sections = inboundBatchStockList.stream().map(b -> b.getSection()).distinct().toList();
        for (SectionModel section : sections) {
            sectionFreeRoomMap.put(section.getId(), getTotalFreeRoom(section));
            inboundTotalVolumeMap.put(section.getId(), 0D);
        }
        for (BatchStockModel batchStock : inboundBatchStockList) {
            inboundTotalVolumeMap.put(batchStock.getSection().getId(), inboundTotalVolumeMap.get(batchStock.getSection().getId()) + product.getUnitVolume() * batchStock.getQuantity());
        }
        List<Long> notFittingSections = new ArrayList<>();
        sections.forEach(s -> {
            if (sectionFreeRoomMap.get(s.getId()) < (inboundTotalVolumeMap.get(s.getId()))) {
                notFittingSections.add(s.getId());
            }
        });
        if (!notFittingSections.isEmpty()) {
            throw new NotEnoughSpaceInSectionException("Section(s) " + notFittingSections + " have not enough space.");
        }
    }

    /**
     * Return total free space of section
     *
     * @param section the section
     * @return total free room
     * @throws Exception
     */
    private Double getTotalFreeRoom(SectionModel section) {
        List<BatchStockModel> batchStockList = getBySectionId(section.getId());
        double freeRoom = section.getTotalSize();
        for (BatchStockModel batchStock : batchStockList) {
            freeRoom -= batchStock.getQuantity() * batchStock.getProduct().getUnitVolume();
        }
        return freeRoom;
    }

    /**
     * Validates if BatchStock category and size are valid
     *
     * @param product the product
     * @param batchStockList the list of BatchStock
     * @throws Exception
     */
    @Override
    public void validateBatches(ProductModel product, List<BatchStockModel> batchStockList) throws ProductNotPermittedInSectionException, NotEnoughSpaceInSectionException {
        for (BatchStockModel batchStock : batchStockList) {
            batchStock.setSection(iSectionService.getById(batchStock.getSection().getId()));
        }
        isCategoryPermittedInSections(product.getCategory(), batchStockList);
        productFitsInSection(product, batchStockList);
    }

    /**
     * Returns a BatchStock list filtered by a product and due date greater than or equal to the informed date
     *
     * @param productModel the product
     * @param dateToCompare the date
     * @return a list of BatchStock
     */
    @Override
    public List<BatchStockModel> findValidProductsByDueDate(Long productModel, LocalDate dateToCompare) {
        getByProductId(productModel);
        return this.batchStockRepository.findProducts(productModel, dateToCompare);
    }

    /**
     * Returns a Batch Stock list with due date between the current day and three weeks ahead given a product
     *
     * @param productModel the product
     * @return list of BatchStock
     * @throws Exception
     */
    private List<BatchStockModel> findValidProductsByDueDate(ProductModel productModel) {
        return batchStockRepository.findByProductAndDueDateGreaterThanEqual(productModel, LocalDate.now().plusWeeks(3));
    }

    /**
     * Consume BatchStock on PurchaseOrder
     *
     * @param purchaseOrderModel the PurchaseOrder
     * @throws Exception
     */
    @Override
    public void consumeBatchStockOnPurchase(PurchaseOrderModel purchaseOrderModel) throws NotEnoughStockException {
        List<OrderProductsModel> orderProductsList = iOrderProductService.getByPurchaseId(purchaseOrderModel.getId());

        for (OrderProductsModel orderProducts : orderProductsList) {
            debitBatchStock(orderProducts.getProductModel(), orderProducts.getQuantity());
        }
    }

    @Override
    public BatchStockModel updateBatchStock(BatchStockModel batchStock, Long batchStockId) throws ProductNotPermittedInSectionException, NotEnoughSpaceInSectionException {
        BatchStockModel savedBatchStock = getById(batchStockId);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStock.setId(savedBatchStock.getId());
        batchStock.setProduct(savedBatchStock.getProduct());
        batchStock.setSection(savedBatchStock.getSection());
        batchStockList.add(batchStock);
        validateBatches(savedBatchStock.getProduct(), batchStockList);
        return batchStockRepository.save(batchStock);
    }

    /**
     * Debit the quantity of the PurchaseOrder product from BatchStock
     *
     * @param productModel the BatchStock
     * @param quantity the quantity of product
     * @throws Exception when insufficient stock
     */
    private void debitBatchStock(ProductModel productModel, Integer quantity) throws NotEnoughStockException {
        List<BatchStockModel> batchStockList = findValidProductsByDueDate(productModel);
        batchStockList.sort(Comparator.comparing(BatchStockModel::getDueDate));

        for (BatchStockModel batchStock : batchStockList) {
            Integer batchStockQuantity = batchStock.getQuantity();
            if (quantity <= batchStockQuantity) {
                batchStock.setQuantity(batchStock.getQuantity() - quantity);
                quantity = 0;
                break;
            } else {
                batchStock.setQuantity(0);
                quantity -= batchStockQuantity;
            }
        }

        if (quantity != 0) {
            throw new NotEnoughStockException("Estoque insuficiente para atender o pedido!");
        }

        save(batchStockList);
    }

    /**
     * Return a List BatchStockModel by ProductId and
     *
     * @param id the ProductModel id
     * @param order list sorting
     * @return BatchStockModel
     * @throws BatchStockFilterOrderInvalidException - Filter order invalid
     */
    @Override
    public List<BatchStockModel> getByProductOrder(Long id, String order) {
        List<BatchStockModel> batchStockList = findValidProductsByDueDate(id, LocalDate.now().plusDays(21));
        switch (order.toUpperCase()){
            case "L":
                batchStockList = batchStockList.stream()
                        .sorted(Comparator.comparing(BatchStockModel::getBatchNumber))
                        .collect(Collectors.toList());
                break;
            case "Q":
                batchStockList = batchStockList.stream()
                        .sorted(Comparator.comparing(BatchStockModel::getQuantity))
                        .collect(Collectors.toList());
                break;
            case "V":
                batchStockList = batchStockList.stream()
                        .sorted(Comparator.comparing(BatchStockModel::getDueDate))
                        .collect(Collectors.toList());
                break;
            default:
                throw new BatchStockFilterOrderInvalidException(order);
        }
        return batchStockList;
    }
}