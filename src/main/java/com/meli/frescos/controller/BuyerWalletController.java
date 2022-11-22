package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BuyerWalletRequest;
import com.meli.frescos.controller.dto.BuyerWalletResponse;
import com.meli.frescos.model.BuyerWalletModel;
import com.meli.frescos.service.BuyerWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/wallet")
public class BuyerWalletController {

    private final BuyerWalletService buyerWalletService;

    public BuyerWalletController(BuyerWalletService buyerWalletService) {
        this.buyerWalletService = buyerWalletService;
    }

    @PostMapping
    public ResponseEntity<BuyerWalletResponse> save(@RequestBody BuyerWalletRequest buyerWalletRequest) {

        BuyerWalletModel buyerWalletModel = buyerWalletService.save(buyerWalletRequest.toModel(), buyerWalletRequest.getBuyerId());

        return new ResponseEntity<>(BuyerWalletResponse.toResponse(buyerWalletModel), HttpStatus.CREATED);
    }
}
