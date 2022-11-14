package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.*;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.service.IBatchStockService;
import com.meli.frescos.service.IProductService;
import com.meli.frescos.service.IRepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final IProductService iProductService;

    private final IRepresentativeService iRepresentativeService;

    private final IBatchStockService iBatchStockService;

    public ProductController(IProductService iProductService, IRepresentativeService iRepresentativeService, IBatchStockService iBatchStockService) {
        this.iProductService = iProductService;
        this.iRepresentativeService = iRepresentativeService;
        this.iBatchStockService = iBatchStockService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() throws Exception {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (ProductModel product : iProductService.getAll()) {
            productResponseList.add(ProductResponse.toResponse(product, iBatchStockService.getTotalBatchStockQuantity(product.getId()), iBatchStockService.getClosestDueDate(product.getId())));
        }
        return new ResponseEntity<>(productResponseList, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailedResponse> getById(@PathVariable Long id) throws Exception {
        ProductModel product = iProductService.getById(id);
        List<BatchStockModel> batchStockList = iBatchStockService.getByProductId(id);
        return new ResponseEntity<>(ProductDetailedResponse.toResponse(product, batchStockList), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<ProductBatchStockResponse> save(@RequestBody ProductBatchStockRequest productBatchStockRequest) throws Exception {
        iRepresentativeService.validateRepresentative(productBatchStockRequest.getInboundOrder().getRepresentativeCode(), productBatchStockRequest.getInboundOrder().getWarehouseCode());
        ProductModel requestProduct = productBatchStockRequest.toProduct();
        List<BatchStockModel> requestBatchStockList = productBatchStockRequest.toBatchStock();
        iBatchStockService.validateBatches(requestProduct, requestBatchStockList);
        requestProduct = iProductService.save(requestProduct);
        for (BatchStockModel requestBatchStock : requestBatchStockList) {
            requestBatchStock.setProduct(requestProduct);
            requestBatchStock = iBatchStockService.save(requestBatchStock);
        }
        return new ResponseEntity<>(ProductBatchStockResponse.toResponse(requestProduct, requestBatchStockList), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getByCategory(@RequestParam("querytype") String filter) throws Exception {
        List<ProductModel> products = iProductService.getByCategory(filter);

        if (products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<ProductResponse> productResponse = new ArrayList<>();

        for (ProductModel product : products) {
            productResponse.add(ProductResponse.toResponse(product, iBatchStockService.getTotalBatchStockQuantity(product.getId()), iBatchStockService.getClosestDueDate(product.getId())));
        }

        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

}
