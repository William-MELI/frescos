package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BuyerWalletRequest;
import com.meli.frescos.controller.dto.BuyerWalletResponse;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.model.BuyerWalletModel;
import com.meli.frescos.service.BuyerService;
import com.meli.frescos.service.BuyerWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Using Spring ResController
 * All endpoints related an Buyer wallet
 */
@RestController
@RequestMapping("/buyer/wallet")
public class BuyerWalletController {

    private final BuyerWalletService buyerWalletService;


    public BuyerWalletController(BuyerWalletService buyerWalletService) {
        this.buyerWalletService = buyerWalletService;
    }

    /**
     * This method create an wallet to buyer already exist.
     * @param buyerWalletRequest
     * @return buyerWalletModel with status 201
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<BuyerWalletResponse> save(@RequestBody BuyerWalletRequest buyerWalletRequest) throws Exception {

        BuyerWalletModel buyerWalletModel = buyerWalletService.save(buyerWalletRequest.toModel(), buyerWalletRequest.getBuyerId());

        return new ResponseEntity<>(BuyerWalletResponse.toResponse(buyerWalletModel), HttpStatus.CREATED);
    }

    /**
     * This method get wallet from buyer
     * @param buyerModel
     * @return BuyerWalletResponse with status 200
     */
    @GetMapping
    public ResponseEntity<BuyerWalletResponse> getByBuyer(@RequestBody BuyerModel buyerModel) {
        List<BuyerWalletModel> buyerWalletModel = buyerWalletService.getByBuyer(buyerModel);
        return new ResponseEntity<>(BuyerWalletResponse.toResponse(buyerWalletModel.get(0)), HttpStatus.OK);
    }

}
