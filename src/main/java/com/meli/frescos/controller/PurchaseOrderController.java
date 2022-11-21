package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.controller.dto.PurchaseOrderResponse;
import com.meli.frescos.exception.NotEnoughStockException;
import com.meli.frescos.model.OrderStatusEnum;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.service.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * All endpoints related an purchase order
 * Using Spring RestController
 */
@RestController
@RequestMapping("purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    /**
     * Endpoint to save a purchase order giving an PurchaseOrderRequest
     * return BigDecimal
     * @param purchaseOrderRequest from PurchaseOrderModel instance
     * @return sum price of all purchase order price
     * @throws Exception
     */
    @PostMapping
    ResponseEntity<PurchaseOrderResponse> save(@RequestBody @Valid PurchaseOrderRequest purchaseOrderRequest) {

        BigDecimal insertPurchase = purchaseOrderService.savePurchaseGetPrice(purchaseOrderRequest.toModel());

        return new ResponseEntity<>(PurchaseOrderResponse.toResponse(insertPurchase), HttpStatus.CREATED);
    }


    /**
     * Endpoint to update Status from purchase order
     * @param id
     * @return status 200 OK
     * @throws Exception
     */
    @PatchMapping("/{id}")
    ResponseEntity<Void> updateStatus(@PathVariable Long id) throws NotEnoughStockException {
        purchaseOrderService.updateStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint to get all purchaseOrder
     * @return List of PurchaseModel instance and Status 200 OK
     */
    @GetMapping
    ResponseEntity<List<PurchaseOrderModel>> getAll() {
        List<PurchaseOrderModel> purchaseOrderModels = purchaseOrderService.getAll();
        return new ResponseEntity<>(purchaseOrderModels, HttpStatus.OK);
    }
}
