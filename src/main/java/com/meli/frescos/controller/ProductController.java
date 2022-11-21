package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.*;
import com.meli.frescos.exception.*;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.service.IBatchStockService;
import com.meli.frescos.service.IProductService;
import com.meli.frescos.service.IRepresentativeService;
import com.meli.frescos.service.IWarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @RestController to Product
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final IProductService iProductService;

    private final IRepresentativeService iRepresentativeService;

    private final IBatchStockService iBatchStockService;

    private final IWarehouseService iWarehouseService;

    public ProductController(IProductService iProductService, IRepresentativeService iRepresentativeService, IBatchStockService iBatchStockService, IWarehouseService iWarehouseService) {
        this.iProductService = iProductService;
        this.iRepresentativeService = iRepresentativeService;
        this.iBatchStockService = iBatchStockService;
        this.iWarehouseService = iWarehouseService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() throws NullDueDateException {
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (ProductModel product : iProductService.getAll()) {
            productResponseList.add(ProductResponse.toResponse(product, iBatchStockService.getTotalBatchStockQuantity(product.getId()), iBatchStockService.getClosestDueDate(product.getId())));
        }
        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    /**
     * Endpoint to return a Product given id
     * @param id the Product id
     * @return a Product related ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailedResponse> getById(@PathVariable Long id) {
        ProductModel product = iProductService.getById(id);
        List<BatchStockModel> batchStockList = iBatchStockService.getByProductId(id);
        List<SimplifiedBatchStockResponse> stockResponse = new ArrayList<>();

        for (BatchStockModel stock : batchStockList) {
            SimplifiedBatchStockResponse response = new SimplifiedBatchStockResponse(stock.getSection().getId(), iBatchStockService.getTotalBatchStockQuantity(product.getId()));
            if (!stockResponse.stream().anyMatch(x -> x.getSectionId() == response.getSectionId() && x.getProductQuantity() == response.getProductQuantity())) {
                stockResponse.add(response);
            }
        }
        return new ResponseEntity<>(ProductDetailedResponse.toResponse(product, stockResponse) , HttpStatus.OK);
    }

    @PostMapping("inboundorder")
    public ResponseEntity<ProductBatchStockResponse> save(@RequestBody ProductBatchStockRequest productBatchStockRequest) throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException, ProductNotPermittedInSectionException, NotEnoughSpaceInSectionException {
        iWarehouseService.getById(productBatchStockRequest.getInboundOrder().getWarehouseCode());
        iRepresentativeService.validateRepresentative(productBatchStockRequest.getInboundOrder().getRepresentativeCode(), productBatchStockRequest.getInboundOrder().getWarehouseCode());
        ProductModel requestProduct = productBatchStockRequest.toProduct();
        List<BatchStockModel> requestBatchStockList = productBatchStockRequest.toBatchStock();
        iBatchStockService.validateBatches(requestProduct, requestBatchStockList);
        requestProduct = iProductService.save(requestProduct);
        for (BatchStockModel requestBatchStock : requestBatchStockList) {
            requestBatchStock.setProduct(requestProduct);
            requestBatchStock = iBatchStockService.save(requestBatchStock);
        }
        return new ResponseEntity<>(ProductBatchStockResponse.toResponse(requestProduct, requestBatchStockList), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getByCategory(@RequestParam("querytype") String filter) throws NullDueDateException {
        List<ProductModel> products = iProductService.getByCategory(filter);

        if (products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<ProductResponse> productResponse = new ArrayList<>();

        for (ProductModel product : products) {
            productResponse.add(ProductResponse.toResponse(product, iBatchStockService.getTotalBatchStockQuantity(product.getId()), iBatchStockService.getClosestDueDate(product.getId())));
        }

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

}
