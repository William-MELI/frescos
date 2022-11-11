package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.BatchStockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
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

    private final IRepresentativeService iRepresentativeService;

    private final IOrderProductService iOrderProductService;

    public BatchStockService(BatchStockRepository batchStockRepository, IProductService iProductService, ISectionService iSectionService, IRepresentativeService iRepresentativeService, IOrderProductService iOrderProductService) {
        this.batchStockRepository = batchStockRepository;
        this.iProductService = iProductService;
        this.iSectionService = iSectionService;
        this.iRepresentativeService = iRepresentativeService;
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
    public BatchStockModel save(BatchStockModel batchStock, Long productId, Long sectionId, Long representativeId, Long warehouseId) throws Exception {
        if (!iRepresentativeService.permittedRepresentative(iRepresentativeService.getById(representativeId), warehouseId)) {
            throw new Exception("Representative does not belong to this warehouse!");
        }

        ProductModel product = iProductService.getById(productId);
        SectionModel section = iSectionService.getById(sectionId);

        batchStock.setProduct(product);
        batchStock.setSection(section);

        return batchStockRepository.save(batchStock);
    }

    public List<BatchStockModel> save(List<BatchStockModel> batchStockList) throws Exception {
        for (BatchStockModel batchStock : batchStockList) {
            if (batchStock.getSection() == null || batchStock.getProduct() == null) {
                throw new Exception("BatchStock inválido!");
            }
        }
        return batchStockRepository.saveAll(batchStockList);
    }

    @Override
    public List<BatchStockModel> findByProductId(Long productId) throws Exception {
        ProductModel product = iProductService.getById(productId);
        return batchStockRepository.findByProduct(product);
    }

    @Override
    public List<BatchStockModel> findBySectionId(Long sectionId) throws Exception {
        SectionModel section = iSectionService.getById(sectionId);
        return batchStockRepository.findBySection(section);
    }

    @Override
    public Integer getTotalBatchStockQuantity(Long productId) throws Exception {
        return findByProductId(productId).stream().mapToInt(BatchStockModel::getQuantity).sum();
    }

    private boolean isCategoryPermittedInSection(CategoryEnum category, Long sectionId) throws Exception {
        return category.equals(iSectionService.getById(sectionId).getCategory());
    }

    private boolean productFitsInSection(ProductModel product, List<BatchStockModel> inboundBtchStockList, Long sectionId) throws Exception {
        double totalInboundVolume = product.getUnitVolume() * inboundBtchStockList.stream().mapToInt(BatchStockModel::getQuantity).sum();
        return totalInboundVolume <= iSectionService.getById(sectionId).getTotalSize() - getTotalUsedRoom(sectionId);
    }

    private Double getTotalUsedRoom(Long sectionId) throws Exception {
        List<BatchStockModel> batchStockList = findBySectionId(sectionId);
        double usedRoom = 0D;
        for (BatchStockModel batchStock : batchStockList) {
            usedRoom += batchStock.getQuantity() * batchStock.getProduct().getUnitVolume();
        }
        return usedRoom;
    }

    @Override
    public boolean isValid(ProductModel product, List<BatchStockModel> batchStockList, Long sectionId) throws Exception {
        return isCategoryPermittedInSection(product.getCategory(), sectionId) && productFitsInSection(product, batchStockList, sectionId);
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
            throw new Exception("Estoque não suficiente para atender o pedido!");
        }

        save(batchStockList);
    }
}
