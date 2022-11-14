package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.BatchStockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
     * Return BatchStockModel given id
     *
     * @param id the batchStockModel id
     * @return BatchStockModel
     * @throws BatchStockByIdNotFoundException - BatchStock not found
     */
    @Override
    public BatchStockModel getById(Long id) throws BatchStockByIdNotFoundException {
        return batchStockRepository.findById(id).orElseThrow(() -> new BatchStockByIdNotFoundException(id));
    }

    @Override
    public BatchStockModel save(BatchStockModel batchStock) throws Exception {
        batchStock.setSection(iSectionService.getById(batchStock.getSection().getId()));
        return batchStockRepository.save(batchStock);
    }

    private List<BatchStockModel> save(List<BatchStockModel> batchStockList) throws Exception {
        for (BatchStockModel batchStock : batchStockList) {
            if (batchStock.getSection() == null || batchStock.getProduct() == null) {
                throw new Exception("BatchStock inválido!");
            }
        }
        return batchStockRepository.saveAll(batchStockList);
    }

    @Override
    public List<BatchStockModel> getByProductId(Long productId) throws Exception {
        ProductModel product = iProductService.getById(productId);
        return batchStockRepository.findByProduct(product);
    }

    @Override
    public List<BatchStockModel> getBySectionId(Long sectionId) throws Exception {
        SectionModel section = iSectionService.getById(sectionId);
        return batchStockRepository.findBySection(section);
    }

    @Override
    public List<BatchStockModel> getBySectionIdAndDueDate(Long sectionId, Integer numberOfDays) throws Exception {
        SectionModel section = iSectionService.getById(sectionId);
        return batchStockRepository.findBySectionAndDueDateBetween(section, LocalDate.now(), LocalDate.now().plusDays(numberOfDays));
    }

    @Override
    public Integer getTotalBatchStockQuantity(Long productId) throws Exception {
        return getByProductId(productId).stream().mapToInt(BatchStockModel::getQuantity).sum();
    }

    @Override
    public LocalDate getClosestDueDate(Long productId) throws Exception {
        return getByProductId(productId).stream().min(Comparator.comparing(BatchStockModel::getDueDate)).orElseThrow(() -> new Exception("Null DueDate on database!")).getDueDate();
    }

    private void isCategoryPermittedInSections(CategoryEnum category, List<BatchStockModel> batchStockList) throws Exception {
        List<Long> notPermitedSections = new ArrayList<>();
        batchStockList.forEach(b -> {
            if(!b.getSection().getCategory().equals(category)) {
                notPermitedSections.add(b.getSection().getId());
            }
        });

        if (!notPermitedSections.isEmpty()) {
            throw new Exception("This product is not permited in these sections: " + notPermitedSections);
        }
    }

    private void productFitsInSection(ProductModel product, List<BatchStockModel> inboundBatchStockList) throws Exception {
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
            throw new Exception("Section(s) " + notFittingSections + " have not enough space.");
        }
    }

    private Double getTotalFreeRoom(SectionModel section) throws Exception {
        List<BatchStockModel> batchStockList = getBySectionId(section.getId());
        double freeRoom = section.getTotalSize();
        for (BatchStockModel batchStock : batchStockList) {
            freeRoom -= batchStock.getQuantity() * batchStock.getProduct().getUnitVolume();
        }
        return freeRoom;
    }

    @Override
    public void validateBatches(ProductModel product, List<BatchStockModel> batchStockList) throws Exception {
        for (BatchStockModel batchStock : batchStockList) {
            batchStock.setSection(iSectionService.getById(batchStock.getSection().getId()));
        }
        isCategoryPermittedInSections(product.getCategory(), batchStockList);
        productFitsInSection(product, batchStockList);
    }

    @Override
    public List<BatchStockModel> findValidProductsByDueDate(Long productModel, LocalDate dateToCompare) {
        return this.batchStockRepository.findProducts(productModel, dateToCompare);
    }

    private List<BatchStockModel> findValidProductsByDueDate(ProductModel productModel) throws Exception {
        return batchStockRepository.findByProductAndDueDateGreaterThanEqual(productModel, LocalDate.now().plusWeeks(3));
    }

    public void consumeBatchStockOnPurchase(PurchaseOrderModel purchaseOrderModel) throws Exception {
        List<OrderProductsModel> orderProductsList = iOrderProductService.getByPurchaseId(purchaseOrderModel.getId());

        for (OrderProductsModel orderProducts : orderProductsList) {
            debitBatchStock(orderProducts.getProductModel(),orderProducts.getQuantity());
        }
    }

    private void debitBatchStock(ProductModel productModel, Integer quantity) throws Exception {
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
            throw new Exception("Estoque insuficiente para atender o pedido!");
        }

        save(batchStockList);
    }
}
