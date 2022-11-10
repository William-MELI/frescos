package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.repository.BatchStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  This class contains all BatchStock related functions
 *  Using @Service from spring
 */
@Service
public class BatchStockService implements IBatchStockService {

    private final BatchStockRepository batchStockRepository;

    private final IProductService iProductService;

    private final ISectionService iSectionService;

    private final IRepresentativeService iRepresentativeService;

    public BatchStockService(BatchStockRepository batchStockRepository, IProductService iProductService, ISectionService iSectionService, IRepresentativeService iRepresentativeService) {
        this.batchStockRepository = batchStockRepository;
        this.iProductService = iProductService;
        this.iSectionService = iSectionService;
        this.iRepresentativeService = iRepresentativeService;
    }

    /**
     * Return all BatchStocks
     * @return List of BatchStockModel
     */
    @Override
    public List<BatchStockModel> findAll() {
        List<BatchStockModel> batchStockList = batchStockRepository.findAll();
        return batchStockList;
    }

    /**
     * Return BatchStockModel given id
     * @param id the batchStockModel id
     * @return BatchStockModel
     * @throws BatchStockByIdNotFoundException
     */
    @Override
    public BatchStockModel findById(Long id) throws BatchStockByIdNotFoundException {
        return batchStockRepository.findById(id).orElseThrow(() -> new BatchStockByIdNotFoundException(id));
    }

    @Override
    public BatchStockModel save(BatchStockModel batchStock, Long productId, Long sectionId, Long representativeId, Long warehouseId) throws Exception {
        if(!iRepresentativeService.permittedRepresentative(iRepresentativeService.getById(representativeId), warehouseId)) {
            throw new Exception("Representative does not belong to this warehouse!");
        }

        ProductModel product = iProductService.getById(productId);
        SectionModel section = iSectionService.findById(sectionId);

        batchStock.setProduct(product);
        batchStock.setSection(section);

        return batchStockRepository.save(batchStock);
    }

    @Override
    public List<BatchStockModel> findByProduct(Long productId) throws Exception {
        ProductModel product = iProductService.getById(productId);
        return batchStockRepository.findByProduct(product);
    }

    @Override
    public Integer getTotalBatchStockQuantity(Long productId) throws Exception {
        return findByProduct(productId).stream().mapToInt(BatchStockModel::getQuantity).sum();
    }
}
