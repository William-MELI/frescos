package com.meli.frescos.service;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.repository.BatchStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchStockService {

    private final BatchStockRepository batchStockRepository;

    public BatchStockService(BatchStockRepository batchStockRepository) {
        this.batchStockRepository = batchStockRepository;
    }

    public List<BatchStockModel> findAll() {
        List<BatchStockModel> batchStockList = batchStockRepository.findAll();
        return batchStockList;
    }

    public BatchStockModel findById(Long id) throws Exception {
        return batchStockRepository.findById(id).orElseThrow(() -> new Exception("erro"));
    }
}
