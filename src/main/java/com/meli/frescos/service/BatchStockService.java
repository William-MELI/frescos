package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.repository.BatchStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchStockService implements IBatchStockService {

    private final BatchStockRepository batchStockRepository;

    public BatchStockService(BatchStockRepository batchStockRepository) {
        this.batchStockRepository = batchStockRepository;
    }

    @Override
    public List<BatchStockModel> findAll() throws Exception {
        List<BatchStockModel> batchStockList = batchStockRepository.findAll();
        if(batchStockList.isEmpty()) {
            throw new Exception("Nenhum lote encontrado");
        }
        return batchStockList;
    }

    @Override
    public BatchStockModel findById(Long id) throws BatchStockByIdNotFoundException {
        return batchStockRepository.findById(id).orElseThrow(() -> new BatchStockByIdNotFoundException(id));
    }
}
