package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.controller.dto.SellerRatingRequest;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.*;
import com.meli.frescos.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SellerRatingControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IBuyerService buyerService;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ISellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ISellerRatingService sellerRatingService;

    @Autowired
    private SellerRatingRepository sellerRatingRepository;

    private SellerModel newSellerRecord(String cpf) {
        SellerModel newSeller = new SellerModel("Joaquim", cpf, null);
        return sellerService.save(newSeller);
    }

    private BuyerModel newBuyerRecord(String cpf){
        BuyerModel buyerModel = new BuyerModel("Laura", cpf);
        return buyerService.save(buyerModel);
    }

    private PurchaseOrderModel newPurchaseOrder(BuyerModel buyer){
        PurchaseOrderModel purchaseOrder = new PurchaseOrderModel(1L, LocalDate.now(), OrderStatusEnum.CLOSED, buyer);
        List<OrderProductsRequest> productsRequestList = new ArrayList<>();
        productsRequestList.add(new OrderProductsRequest(1L, 2, purchaseOrder.getId()));
        PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
                .date(purchaseOrder.getDate())
                .buyer(purchaseOrder.getBuyer().getId())
                .products(productsRequestList).build();

        return purchaseOrderService.save(purchaseOrderRequest);
    }

    @BeforeEach
    void setup() {
        /*this.sellerRepository.deleteAllInBatch();
        this.buyerRepository.deleteAllInBatch();
        this.purchaseOrderRepository.deleteAllInBatch();
        this.sellerRatingRepository.deleteAllInBatch();*/
    }

    @Test
    @DisplayName("Test SellerRating Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
        SellerModel seller = newSellerRecord("12345678900");
        BuyerModel buyer = newBuyerRecord("78965412300");
        PurchaseOrderModel purchaseOrder = newPurchaseOrder(buyer);
        purchaseOrderService.updateStatus(purchaseOrder.getId());

        Double rating = 4.8;

        SellerRatingRequest sellerRatingRequest = SellerRatingRequest.builder()
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .purchaseOrderId(purchaseOrder.getId())
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller-rating")
                        .content(objectMapper.writeValueAsString(sellerRatingRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test Get all SellerRating - GET Endpoint")
    void getAll_returnListOfSellerRating_whenSuccess() throws Exception {
        SellerModel seller = newSellerRecord("11122233344");
        BuyerModel buyer = newBuyerRecord("33322211100");
        PurchaseOrderModel purchaseOrder = newPurchaseOrder(buyer);
        purchaseOrderService.updateStatus(purchaseOrder.getId());
        Double rating = 3.8;

        SellerRatingModel sellerRatingModel = new SellerRatingModel(2L, seller, purchaseOrder, buyer, rating);

        sellerRatingService.save(sellerRatingModel);

        ResultActions response = mockMvc.perform(
                get("/seller-rating")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }
}