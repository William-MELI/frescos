package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.*;
import com.meli.frescos.exception.NullDueDateException;
import com.meli.frescos.exception.ProductByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.service.IBatchStockService;
import com.meli.frescos.service.IProductService;
import com.meli.frescos.service.IRepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @RestController to Product
 */
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

    /**
     * Return all Product
     * Return 200 OK when operation is success
     *
     * @return a list with all ProductResponse instance
     */
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
     *
     * @param id the Product id
     * @return a Product related ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailedResponse> getById(@PathVariable Long id) throws ProductByIdNotFoundException {
        ProductModel product = iProductService.getById(id);
        List<BatchStockModel> batchStockListWithDuplicatedSections = iBatchStockService.getAll().stream().filter(x -> x.getProduct().getId() == product.getId()).toList();
        List<SimplifiedBatchStockResponse> stockResponseList = new ArrayList<>();

        batchStockListWithDuplicatedSections.forEach(x -> stockResponseList.add(new SimplifiedBatchStockResponse(x.getSection().getId(), x.getQuantity())));

        for (BatchStockModel stock : batchStockListWithDuplicatedSections) {
            List<SimplifiedBatchStockResponse> stockResponseFilteredBySectionId = stockResponseList.stream().filter(x -> x.getSectionId() == stock.getSection().getId()).toList();

            if(stockResponseFilteredBySectionId.size() > 1) {
                Integer totalStockProductQuantity = stockResponseFilteredBySectionId.stream().mapToInt(SimplifiedBatchStockResponse::getProductQuantity).sum();
                stockResponseList.removeIf(x -> x.getSectionId() == stock.getSection().getId());
                stockResponseList.add(new SimplifiedBatchStockResponse(stock.getSection().getId(), totalStockProductQuantity));
            }
        }
        return new ResponseEntity<>(ProductDetailedResponse.toResponse(product, stockResponseList), HttpStatus.OK);
    }

    /**
     * Creates a new Product instance.
     * Returns 201 CREATED when operation is success
     *
     * @param productBatchStockRequest ProductBatchStockRequest instance
     * @return a ProductBatchStockResponse instance
     */
    @PostMapping("/inboundorder")
    public ResponseEntity<ProductBatchStockResponse> save(@Valid @RequestBody ProductBatchStockRequest productBatchStockRequest) throws Exception {
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

    /**
     * Returns the product filtered by category
     * Return 200 OK when operation is success
     *
     * @return a list with all ProductResponse instance
     */
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

    /**
     * Returns the products filtered by description and sorted by top rating seller
     * @param description title to be searched
     * @return list of all products that contain the searched text in the description
     * @throws NullDueDateException when there are no products within the due date
     */
    @GetMapping("description-containing")
    public ResponseEntity<List<ProductContainDescriptionResponse>> getByDescriptionContaining(@RequestParam String description) throws NullDueDateException {
        List<ProductModel> products = iProductService.getByDescriptionContaining(description);

        List<ProductContainDescriptionResponse> productResponse = new ArrayList<>();

        for (ProductModel product : products) {
            productResponse.add(ProductContainDescriptionResponse.toResponse(product, iBatchStockService.getClosestDueDate(product.getId())));
        }
        productResponse = productResponse.stream().sorted(Comparator.comparing(ProductContainDescriptionResponse::getRatingSeller).reversed()).collect(Collectors.toList());
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


}
