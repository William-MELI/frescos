package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.*;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.service.IBatchStockService;
import com.meli.frescos.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final IProductService iProductService;

    private final IBatchStockService iBatchStockService;

    private final BatchStockController batchStockController;

    public ProductController(IProductService iProductService, IBatchStockService iBatchStockService, BatchStockController batchStockController1) {
        this.iProductService = iProductService;
        this.iBatchStockService = iBatchStockService;
        this.batchStockController = batchStockController1;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() throws Exception {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (ProductModel product : iProductService.getAll()) {
            productResponseList.add(ProductResponse.toResponse(product, iBatchStockService.getTotalBatchStockQuantity(product.getId())));
        }
        return new ResponseEntity<>(productResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) throws Exception {
        ProductModel product = iProductService.getById(id);
        return new ResponseEntity<>(ProductResponse.toResponse(product, iBatchStockService.getTotalBatchStockQuantity(id)), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductBatchStockResponse> save(@RequestBody ProductBatchStockRequest productBatchStockRequest) throws Exception {
        boolean validProduct = iBatchStockService.isValid(productBatchStockRequest.toProduct(), productBatchStockRequest.toBatchStock(), productBatchStockRequest.getInboundOrder().getSectionCode());
        if (!validProduct) {
            throw new Exception("No room available in this section!");
        }
        ProductModel product = iProductService.save(productBatchStockRequest.toProduct(), productBatchStockRequest.getInboundOrder().getSellerCode());
        List<BatchStockResponse> batchStockList = new ArrayList<>();
        for (BatchStockRequest batchStockRequest : productBatchStockRequest.getInboundOrder().getBatchStock()) {
            batchStockList.add(batchStockController.save(batchStockRequest,
                    product.getId(),
                    productBatchStockRequest.getInboundOrder().getSectionCode(),
                    productBatchStockRequest.getInboundOrder().getRepresentativeCode(),
                    productBatchStockRequest.getInboundOrder().getWarehouseCode()).getBody());
        }

        ProductBatchStockResponse productBatchStockResponse = ProductBatchStockResponse.toResponse(product);
        productBatchStockResponse.setBatchStock(batchStockList);

        return new ResponseEntity<>(productBatchStockResponse, HttpStatus.OK);
    }

}
