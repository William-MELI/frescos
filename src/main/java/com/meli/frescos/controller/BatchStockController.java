package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BatchStockOrderResponse;
import com.meli.frescos.controller.dto.BatchStockRequest;
import com.meli.frescos.controller.dto.BatchStockResponse;
import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.service.IBatchStockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
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

        iBatchStockService.getAll().forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));

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

        iBatchStockService.findByProductId(id).forEach(b -> batchStockResponseList.add(BatchStockResponse.toResponse(b)));

        return new ResponseEntity<>(batchStockResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/product-id/{productId}")
    ResponseEntity<BatchStockResponse> getByProductId(@PathVariable Long productId) throws BatchStockByIdNotFoundException {
        return new ResponseEntity<>(BatchStockResponse.toResponse((iBatchStockService.getById(productId))), HttpStatus.FOUND);
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

    @GetMapping("/list")
    public ResponseEntity<List<BatchStockOrderResponse>> getBatchStockByProduct(@RequestParam("idProduct") Long id) {
        List<BatchStockOrderResponse> batchStock = iBatchStockService.findValidProductsByDueDate(id, LocalDate.now().plusDays(21)).stream().map(BatchStockOrderResponse::toResponse).toList();
        if(batchStock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(batchStock, HttpStatus.FOUND);
    }

    @GetMapping("/list/order")
    public ResponseEntity<List<BatchStockOrderResponse>> getBatchStockByProductOrder(@RequestParam("idProduct") Long id, @RequestParam("order") String order) {
        List<BatchStockModel> batchStock = iBatchStockService.findByProductOrder(id, order);
        //List<BatchStockOrderResponse> list = iBatchStockService.findByProductOrder(id, order).stream().map(BatchStockOrderResponse::toResponse).toList();
        if(batchStock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(batchStock.stream().map(BatchStockOrderResponse::toResponse).toList(), HttpStatus.FOUND);
    }
}
