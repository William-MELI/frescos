package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BatchStockFiltersResponse;
import com.meli.frescos.controller.dto.BatchStockOrderResponse;
import com.meli.frescos.controller.dto.BatchStockRequest;
import com.meli.frescos.controller.dto.BatchStockResponse;
import com.meli.frescos.exception.*;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.service.IBatchStockService;
import com.meli.frescos.service.IProductService;
import com.meli.frescos.service.IRepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * Return 200 OK when operation is success
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
     * Return 200 OK when operation is success
     *
     * @param id the backStockModel id
     * @return a BatchStockModel  related ID
     * @throws BatchStockByIdNotFoundException - BatchStock not found
     */
    @GetMapping("/{id}")
    ResponseEntity<BatchStockResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(BatchStockResponse.toResponse((iBatchStockService.getById(id))), HttpStatus.OK);
    }

    /**
     * Return a list of BatchStock given section id and number of days to a BatchStock due date
     * Return 200 OK when operation is success
     *
     * @param sectionId the section id
     * @param numberOfDays number of days to be added to the current day to arrive at the due date to be sought
     * @return a list of BatchStock
     */
    @GetMapping("/section")
    ResponseEntity<List<BatchStockFiltersResponse>> getBySectionDueDate(@RequestParam Long sectionId,
                                                                        @RequestParam Integer numberOfDays) throws Exception {
        List<BatchStockFiltersResponse> batchStockResponseList = iBatchStockService
                .getBySectionIdAndDueDate(sectionId, numberOfDays)
                .stream()
                .sorted(Comparator.comparing(BatchStockModel::getDueDate))
                .map(BatchStockFiltersResponse::toResponse).toList();
        return new ResponseEntity<>(batchStockResponseList, HttpStatus.OK);
    }

    /**
     * Return a list of BatchStock given category and number of days to a BatchStock due date
     * Return 200 OK when operation is success
     *
     * @param category the category
     * @param numberOfDays number of days to be added to the current day to arrive at the due date to be sought
     * @param order order due date
     * @return a list of BatchStock
     */
    @GetMapping("/category")
    ResponseEntity<List<BatchStockFiltersResponse>> getByCategoryDueDate(@RequestParam String category,
                                                                         @RequestParam Integer numberOfDays,
                                                                         @RequestParam String order) throws Exception {

        List<BatchStockFiltersResponse> batchStockResponseList = iBatchStockService
                .getByCategoryAndDueDate(CategoryEnum.getEnum(category), numberOfDays)
                .stream()
                .sorted(order.equalsIgnoreCase("ASC") ? Comparator.comparing(BatchStockModel::getDueDate) : Comparator.comparing(BatchStockModel::getDueDate).reversed())
                .map(BatchStockFiltersResponse::toResponse).toList();
        return new ResponseEntity<>(batchStockResponseList, HttpStatus.OK);
    }

    /**
     * Return BatchStock given id
     * Return 200 OK when operation is success
     *
     * @param productId the batchStockModel id
     * @return BatchStockModel
     * @throws BatchStockByIdNotFoundException when BatchStock not found
     */
    @GetMapping("/product-id/{productId}")
    ResponseEntity<BatchStockResponse> getByProductId(@PathVariable Long productId) throws BatchStockByIdNotFoundException {
        return new ResponseEntity<>(BatchStockResponse.toResponse((iBatchStockService.getById(productId))), HttpStatus.OK);
    }

    /**
     * Create a new BatchStock given model
     * Return 201 CREATED when operation is success
     *
     * @param batchStockRequest new BatchStock to create
     * @param productId the Prosuct id
     * @param representativeId the Representative id
     * @param warehouseId Warehousde id
     * @return the BatchStock created
     */
    @PostMapping("/product-id")
    ResponseEntity<BatchStockResponse> save(@RequestBody @Valid BatchStockRequest batchStockRequest,
                                            @RequestParam Long productId,
                                            @RequestParam Long representativeId,
                                            @RequestParam Long warehouseId) throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException, WarehouseNotFoundException {
        iRepresentativeService.validateRepresentative(representativeId, warehouseId);
        BatchStockModel batchStock = batchStockRequest.toModel();
        batchStock.setProduct(iProductService.getById(productId));
        return new ResponseEntity<>(BatchStockResponse.toResponse(iBatchStockService.save(batchStock)),
                HttpStatus.CREATED);
    }

    /**
     * Returns a Batch Stock list with due date between the current day and three weeks ahead given a product
     * Return 200 OK when operation is success
     *
     * @param id the product
     * @return list of BatchStock
     */
    @GetMapping("/list")
    public ResponseEntity<List<BatchStockOrderResponse>> getBatchStockByProduct(@RequestParam("idProduct") Long id) {
        List<BatchStockOrderResponse> batchStock = iBatchStockService.findValidProductsByDueDate(id, LocalDate.now().now().plusDays(21)).stream().map(BatchStockOrderResponse::toResponse).toList();
        if(batchStock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }

    /**
     * Return a List BatchStockModel by ProductId and
     * Return 200 OK when operation is success
     *
     * @param id the ProductModel id
     * @param order list sorting
     * @return a list with all BatchStockOrderResponse instance
     */
    @GetMapping("/list/order")
    public ResponseEntity<List<BatchStockOrderResponse>> getBatchStockByProductOrder(@RequestParam("idProduct") Long id, @RequestParam("order") String order) {
        List<BatchStockModel> batchStock = iBatchStockService.getByProductOrder(id, order);

        if(batchStock.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(batchStock.stream().map(BatchStockOrderResponse::toResponse).toList(), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<BatchStockResponse> update(@RequestBody @Valid BatchStockRequest batchStockRequest,
                                            @RequestParam Long batchStockId,
                                            @RequestParam Long representativeId,
                                            @RequestParam Long warehouseId) throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException, WarehouseNotFoundException, ProductNotPermittedInSectionException, NotEnoughSpaceInSectionException {
        iRepresentativeService.validateRepresentative(representativeId, warehouseId);
        BatchStockModel batchStock = iBatchStockService.updateBatchStock(batchStockRequest.toModel(), batchStockId);
        return new ResponseEntity<>(BatchStockResponse.toResponse(iBatchStockService.save(batchStock)),
                HttpStatus.CREATED);
    }
}