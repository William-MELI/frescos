package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BatchStockRequest;
import com.meli.frescos.controller.dto.BatchStockResponse;
import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.service.IBatchStockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * All endpoints related to BatchStock
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/batch-stock")
public class BatchStockController {

    private final IBatchStockService iBatchStockService;

    public BatchStockController(IBatchStockService iBatchStockService) {
        this.iBatchStockService = iBatchStockService;
    }

    /**
     * Endpoint to return all BatchStocks
     *
     * @return a List with all BatchStockResponse with status 200 ok
     */
    @GetMapping
    ResponseEntity<List<BatchStockResponse>> getAll() {
        List<BatchStockResponse> batchStockResponseList = new ArrayList<>();

        iBatchStockService.findAll().forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));

        return new ResponseEntity<>(batchStockResponseList, HttpStatus.FOUND);
    }

    /**
     * Endpoint to return a backStockModel given id
     *
     * @param id the backStockModel id
     * @return a BatchStockModel  related ID
     * @throws BatchStockByIdNotFoundException - BatchStock not found
     */
    @GetMapping("/{id}")
    ResponseEntity<List<BatchStockResponse>> getById(@PathVariable Long id) throws Exception {
        List<BatchStockResponse> batchStockResponseList = new ArrayList<>();

        iBatchStockService.findByProduct(id).forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));

        return new ResponseEntity<>(batchStockResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/product-id/{productId}")
    ResponseEntity<BatchStockResponse> getByProductId(@PathVariable Long productId) throws BatchStockByIdNotFoundException {
        return new ResponseEntity<>(BatchStockResponse.toResponse((iBatchStockService.findById(productId))), HttpStatus.FOUND);
    }

    @PostMapping("/product-id")
    ResponseEntity<BatchStockResponse> save(@RequestBody BatchStockRequest batchStockRequest,
                                            @RequestParam Long productId,
                                            @RequestParam Long sectionId,
                                            @RequestParam Long representativeId,
                                            @RequestParam Long warehouseId) throws Exception {

        return new ResponseEntity<>(BatchStockResponse.toResponse(iBatchStockService.save(batchStockRequest.toModel(),
                productId,
                sectionId,
                representativeId,
                warehouseId)),
                HttpStatus.CREATED);
    }
}
