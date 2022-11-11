package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.controller.dto.PurchaseOrderResponse;
import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.controller.dto.SectionResponse;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.service.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("purchase-order")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController (PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    ResponseEntity<PurchaseOrderResponse> save(@RequestBody @Valid PurchaseOrderRequest purchaseOrderRequest) {

        BigDecimal insertPurchase = purchaseOrderService.getTotalPrice(purchaseOrderRequest.toModel());

        return new ResponseEntity<>(PurchaseOrderResponse.toResponse(insertPurchase), HttpStatus.CREATED);

    }
}
