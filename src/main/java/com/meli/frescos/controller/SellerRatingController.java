package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SellerRatingRequest;
import com.meli.frescos.controller.dto.SellerRatingResponse;
import com.meli.frescos.exception.PurchaseOrderWithIvalidStatusException;
import com.meli.frescos.model.*;
import com.meli.frescos.service.IBuyerService;
import com.meli.frescos.service.IPurchaseOrderService;
import com.meli.frescos.service.ISellerRatingService;
import com.meli.frescos.service.ISellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * All endpoints related to SellerRating
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/seller-rating")
public class SellerRatingController {

    private final ISellerRatingService iSellerRatingService;
    private final ISellerService iSellerService;
    private final IPurchaseOrderService iPurchaseOrderService;
    private final IBuyerService iBuyerService;

    public SellerRatingController(ISellerRatingService iSellerRatingService, ISellerService iSellerService, IPurchaseOrderService iPurchaseOrderService, IBuyerService iBuyerService) {
        this.iSellerRatingService = iSellerRatingService;
        this.iSellerService = iSellerService;
        this.iPurchaseOrderService = iPurchaseOrderService;
        this.iBuyerService = iBuyerService;
    }

    /**
     * Creates a new SellerRating instance.
     * Returns 201 CREATED when operation is success
     * @param sellerRatingRequest the SellerRating instance
     * @return a SellerRatingResponse instance
     */
    @PostMapping
    public ResponseEntity<SellerRatingResponse> save(@Valid @RequestBody SellerRatingRequest sellerRatingRequest) {
        SellerModel seller = iSellerService.getById(sellerRatingRequest.getSellerId());
        PurchaseOrderModel purchaseOrder = iPurchaseOrderService.getById(sellerRatingRequest.getPurchaseOrderId());
        BuyerModel buyer = iBuyerService.getById(sellerRatingRequest.getBuyerId());

        if(purchaseOrder.getOrderStatus().equals(OrderStatusEnum.OPEN))
            throw new PurchaseOrderWithIvalidStatusException("Não é possível realizar a avaliação do vendedor pois a ordem de compra está aberta");

        SellerRatingModel sellerRating = new SellerRatingModel();
        sellerRating.setSeller(seller);
        sellerRating.setPurchaseOrder(purchaseOrder);
        sellerRating.setBuyer(buyer);
        sellerRating.setRating(sellerRatingRequest.getRating());

        return new ResponseEntity<>(SellerRatingResponse.toResponse(iSellerRatingService.save(sellerRating)), HttpStatus.CREATED);
    }

    /**
     * Return all SellerRating
     * Return 200 OK when operation is success
     * @return a list with all SellerRating instance
     */
    @GetMapping
    public ResponseEntity<List<SellerRatingResponse>> getAll(){
        List<SellerRatingResponse> sellerRatingResponseList = iSellerRatingService.getAll().stream().map(SellerRatingResponse::toResponse).toList();
        return new ResponseEntity<>(sellerRatingResponseList, HttpStatus.OK);
    }
}
