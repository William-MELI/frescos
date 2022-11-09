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


@RestController
@RequestMapping("/api/v1/fresh-products/batch-stock")
public class BatchStockController {

    private final BatchStockService batchStockService;

    public BatchStockController(BatchStockService batchStockService) {
        this.batchStockService = batchStockService;
    }

    @GetMapping
    ResponseEntity<List<BatchStockResponse>> getAll() throws Exception {
        List<BatchStockResponse> batchStockResponseList = new ArrayList<>();

        batchStockService.findAll().forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));

        return ResponseEntity.ok(batchStockResponseList);
    }

    @GetMapping("/filter-id")
    ResponseEntity<BatchStockResponse> getById(@RequestParam Long id) throws BatchStockByIdNotFoundException {
        return ResponseEntity.ok(BatchStockResponse.toResponse((batchStockService.findById(id))));
    }
}
