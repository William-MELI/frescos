package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.BuyerTransactionsRequest;
import com.meli.frescos.controller.dto.BuyerTransactionsResponse;
import com.meli.frescos.controller.dto.BuyerWalletResponse;
import com.meli.frescos.model.BuyerTransactionsModel;
import com.meli.frescos.model.BuyerWalletModel;
import com.meli.frescos.service.BuyerTransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Using Spring ResController
 * All endpoints related an Buyer transactions
 */
@RestController
@RequestMapping("/buyer/transaction")
public class BuyerTransactionsController {

    private final BuyerTransactionsService buyerTransactionsService;

    public BuyerTransactionsController(BuyerTransactionsService buyerTransactionsService) {
        this.buyerTransactionsService = buyerTransactionsService;
    }

    /**
     * This method deposit value to your wallet
     * @param buyerTransactionsRequest
     * @param id
     * @return wallet with update value
     */
    @PostMapping("/deposit/{id}")
    public ResponseEntity<BuyerWalletResponse> save(@RequestBody BuyerTransactionsRequest buyerTransactionsRequest,@PathVariable Long id) {

        BuyerWalletModel buyerWalletModel = buyerTransactionsService.saveDeposit(buyerTransactionsRequest.toModel(), id);

        return new ResponseEntity<>(BuyerWalletResponse.toResponse(buyerWalletModel), HttpStatus.CREATED);
    }

    /**
     * This method find all transactions related to buyer id
     * @param id
     * @return List of Transactions by buyer id
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<BuyerTransactionsResponse>> getTransactionsById(@PathVariable Long id) {

        List<BuyerTransactionsModel> buyerTransactionsModels = buyerTransactionsService.getTransactionsByBuyerId(id);

        List<BuyerTransactionsResponse> buyerTransactionsResponses = new ArrayList<>();

        buyerTransactionsModels.forEach(t -> buyerTransactionsResponses.add(BuyerTransactionsResponse.toResponse(t)));

        return new ResponseEntity<>(buyerTransactionsResponses, HttpStatus.OK);
    }
}
