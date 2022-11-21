package com.meli.frescos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.*;
import com.meli.frescos.service.IOrderProductService;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OrderProductControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IOrderProductService orderProductService;

    @Autowired
    private com.meli.frescos.repository.OrderProductsRepository orderProductsRepository;

    @Autowired
    private com.meli.frescos.repository.ProductRepository productRepository;

    @Autowired
    com.meli.frescos.repository.PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    com.meli.frescos.repository.SellerRepository sellerRepository;

    @Autowired
    com.meli.frescos.repository.BuyerRepository buyerRepository;

    @BeforeEach
    void setup() {

        purchaseOrderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderProductsRepository.deleteAllInBatch();
        sellerRepository.deleteAllInBatch();
        buyerRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("Test OrderProduct Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
        BuyerModel buyerModel = insertBuyer();
        SellerModel sellerModel = insertSeller();
        ProductModel productModel = insertProduct(sellerModel);
        PurchaseOrderModel purchaseOrderModel = insertPurchaseOrder(buyerModel);

        OrderProductsRequest orderProductRequest = OrderProductsRequest
                .builder()
                .productModel(productModel.getId())
                .purchaseOrderModel(purchaseOrderModel.getId())
                .quantity(1)
                .build();

        ResultActions response = mockMvc.perform(
                post("/orderProducts")
                        .content(objectMapper.writeValueAsString(orderProductRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test OrderProduct Successfull GetById - GET Endpoint")
    void getById_returnsOKStatus_whenSuccess() throws Exception {
        BuyerModel buyerModel = insertBuyer();
        SellerModel sellerModel = insertSeller();
        ProductModel productModel = insertProduct(sellerModel);
        PurchaseOrderModel purchaseOrderModel = insertPurchaseOrder(buyerModel);
        OrderProductsModel orderProductsModel = insertOrderProduct(productModel, purchaseOrderModel);

        ResultActions response = mockMvc.perform(
                get("/orderProducts/{id}", orderProductsModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test OrderProduct Successfull GetByPurchaseId - GET Endpoint")
    void getByOrderId_returnsOKStatus_whenSuccess() throws Exception {
        BuyerModel buyerModel = insertBuyer();
        SellerModel sellerModel = insertSeller();
        ProductModel productModel = insertProduct(sellerModel);
        PurchaseOrderModel purchaseOrderModel = insertPurchaseOrder(buyerModel);
        insertOrderProduct(productModel, purchaseOrderModel);

        ResultActions response = mockMvc.perform(
                get("/orderProducts/idOrder/{idOrder}", purchaseOrderModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test OrderProduct Successfull getAll - GET Endpoint")
    void getAll_returnsOKStatus_whenSuccess() throws Exception {
        BuyerModel buyerModel = insertBuyer();
        SellerModel sellerModel = insertSeller();
        ProductModel productModel = insertProduct(sellerModel);
        PurchaseOrderModel purchaseOrderModel = insertPurchaseOrder(buyerModel);
        insertOrderProduct(productModel, purchaseOrderModel);

        ResultActions response = mockMvc.perform(
                get("/orderProducts/")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    BuyerModel insertBuyer() {
        Long id = 1L;
        String name = "Buyer";
        String cpf = "12345678900";
        BuyerModel buyer = new BuyerModel(id, name, cpf);

        return buyerRepository.save(buyer);
    }

    SellerModel insertSeller() {
        Long id = 1L;
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;
        SellerModel sellerModel = new SellerModel(id, name, cpf, rating);

        return sellerRepository.save(sellerModel);
    }

    ProductModel insertProduct(SellerModel sellerModel) {
        ProductModel productModel =
                new ProductModel(1L, "Test", "Test", new BigDecimal(1), CategoryEnum.FROZEN, Double.valueOf(1.0), Double.valueOf(1.0), LocalDate.now(), sellerModel);

        return productRepository.save(productModel);
    }

    PurchaseOrderModel insertPurchaseOrder(BuyerModel buyer) {
        PurchaseOrderModel purchaseOrderModel =
                new PurchaseOrderModel(1L, LocalDate.now(), OrderStatusEnum.OPEN, buyer);

        return purchaseOrderRepository.save(purchaseOrderModel);
    }

    OrderProductsModel insertOrderProduct(ProductModel productModel, PurchaseOrderModel purchaseOrderModel) {
        OrderProductsRequest orderProductRequest = OrderProductsRequest
                .builder()
                .productModel(productModel.getId())
                .purchaseOrderModel(purchaseOrderModel.getId())
                .quantity(1)
                .build();
        return orderProductService.save(orderProductRequest);
    }
}