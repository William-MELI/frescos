package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
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

    public BatchStockService(BatchStockRepository batchStockRepository) {
        this.batchStockRepository = batchStockRepository;
    }

    /**
     * Return all BatchStocks
     * @return List of BatchStock
     */
    @Override
    public List<BatchStockModel> findAll() {
        List<BatchStockModel> batchStockList = batchStockRepository.findAll();
        return batchStockList;
    }

    /**
     * Return BatchStockModel given id
     * @param id the batchStockModel id
     * @return BatchStock
     * @throws BatchStockByIdNotFoundException
     */
    @Override
    public BatchStockModel findById(Long id) throws BatchStockByIdNotFoundException {
        return batchStockRepository.findById(id).orElseThrow(() -> new BatchStockByIdNotFoundException(id));
    }
}
