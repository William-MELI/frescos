package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BatchStockOrderResponse;
import com.meli.frescos.controller.dto.BatchStockRequest;
import com.meli.frescos.controller.dto.BatchStockResponse;
import com.meli.frescos.exception.BatchStockByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.service.IBatchStockService;
import com.meli.frescos.service.IProductService;
import com.meli.frescos.service.IRepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * All endpoints related to BatchStock
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/batch-stock")
public class BatchStockController {

    private final IBatchStockService iBatchStockService;
    private final IRepresentativeService iRepresentativeService;
    private final IProductService iProductService;

    public BatchStockController(IBatchStockService iBatchStockService, IRepresentativeService iRepresentativeService, IProductService iProductService) {
        this.iBatchStockService = iBatchStockService;
        this.iRepresentativeService = iRepresentativeService;
        this.iProductService = iProductService;
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

        return new ResponseEntity<>(batchStockResponseList, HttpStatus.OK);
    }

    /**
     * Endpoint to return a backStockModel given id
     *
     * @param id the backStockModel id
     * @return a BatchStockModel  related ID
     * @throws BatchStockByIdNotFoundException - BatchStock not found
     */
    @GetMapping("/{id}")
    ResponseEntity<BatchStockResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(BatchStockResponse.toResponse((iBatchStockService.getById(id))), HttpStatus.OK);
    }

    @GetMapping("/section")
    ResponseEntity<List<BatchStockResponse>> getBySectionDueDate(@RequestParam Long sectionId,
                                                                 @RequestParam Integer numberOfDays) throws Exception {
        List<BatchStockResponse> batchStockResponseList = iBatchStockService
                .getBySectionIdAndDueDate(sectionId, numberOfDays)
                .stream()
                .sorted(Comparator.comparing(BatchStockModel::getDueDate))
                .map(BatchStockResponse::toResponse).toList();
        return new ResponseEntity<>(batchStockResponseList, HttpStatus.OK);
    }

    @GetMapping("/category")
    ResponseEntity<List<BatchStockResponse>> getByCategoryDueDate(@RequestParam CategoryEnum category,
                                                                 @RequestParam Integer numberOfDays) throws Exception {
        List<BatchStockResponse> batchStockResponseList = iBatchStockService
                .getByCategoryAndDueDate(category, numberOfDays)
                .stream()
                .sorted(Comparator.comparing(BatchStockModel::getDueDate))
                .map(BatchStockResponse::toResponse).toList();
        return new ResponseEntity<>(batchStockResponseList, HttpStatus.OK);
    }

    @GetMapping("/product-id/{productId}")
    ResponseEntity<BatchStockResponse> getByProductId(@PathVariable Long productId) throws BatchStockByIdNotFoundException {
        return new ResponseEntity<>(BatchStockResponse.toResponse((iBatchStockService.getById(productId))), HttpStatus.OK);
    }

    @PostMapping("/product-id")
    ResponseEntity<BatchStockResponse> save(@RequestBody BatchStockRequest batchStockRequest,
                                            @RequestParam Long productId,
                                            @RequestParam Long representativeId,
                                            @RequestParam Long warehouseId) throws Exception {
        iRepresentativeService.validateRepresentative(representativeId, warehouseId);
        BatchStockModel batchStock = batchStockRequest.toModel();
        batchStock.setProduct(iProductService.getById(productId));
        return new ResponseEntity<>(BatchStockResponse.toResponse(iBatchStockService.save(batchStock)),
                HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BatchStockOrderResponse>> getBatchStockByProduct(@RequestParam("idProduct") Long id) {
        List<BatchStockOrderResponse> batchStock = iBatchStockService.findValidProductsByDueDate(id, LocalDate.now().now().plusDays(21)).stream().map(BatchStockOrderResponse::toResponse).toList();
        if(batchStock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }

    @GetMapping("/list/order")
    public ResponseEntity<List<BatchStockOrderResponse>> getBatchStockByProductOrder(@RequestParam("idProduct") Long id, @RequestParam("order") String order) {
        List<BatchStockModel> batchStock = iBatchStockService.getByProductOrder(id, order);

        if(batchStock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(batchStock.stream().map(BatchStockOrderResponse::toResponse).toList(), HttpStatus.OK);
    }
}