package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BatchStockResponse;
import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.service.BatchStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * All endpoints related to BatchStock
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/api/v1/fresh-products/batch-stock")
public class BatchStockController {

    private final BatchStockService batchStockService;

    public BatchStockController(BatchStockService batchStockService) {
        this.batchStockService = batchStockService;
    }

    /**
     * Endpoint to return all BatchStocks
     * @return a List with all BatchStockResponse with status 200 ok
     */
    @GetMapping
    ResponseEntity<List<BatchStockResponse>> getAll() {
        List<BatchStockResponse> batchStockResponseList = new ArrayList<>();

        batchStockService.findAll().forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));

        return ResponseEntity.ok(batchStockResponseList);
    }

    /**
     * Endpoint to return a backStockModel given id
     * @param id the backStockModel id
     * @return a BatchStockModel  related ID
     * @throws BatchStockByIdNotFoundException
     */
    @GetMapping("/filter-id")
    ResponseEntity<BatchStockResponse> getById(@RequestParam Long id) throws BatchStockByIdNotFoundException {
        return ResponseEntity.ok(BatchStockResponse.toResponse((batchStockService.findById(id))));
    }
}
