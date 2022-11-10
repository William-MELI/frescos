package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.BatchStockRepository;
import com.meli.frescos.repository.BuyerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class PurchaseOrderServiceTest {

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    BatchStockRepository batchStockRepository;

    @Test
    void save_returnPurchaseOrderModel_whenSuccess() {
        String name = "Buyer";
        String cpf = "41937616576";
        BuyerModel buyer = new BuyerModel(name, cpf);

        String batchNumber = "L-123";
        int quantity = 10;
        LocalDate manufacturingDate = LocalDate.now().minusWeeks(10);
        LocalDateTime manufacturingTime = LocalDateTime.now();
        LocalDate dueTime = LocalDate.now().minusWeeks(10);
        ProductModel product = new ProductModel(1L,
                "Camiseta",
                "Test",
                new BigDecimal(10),
                CategoryEnum.FROZEN,
                1.0,
                1.0,
                LocalDate.now(),
                null);

        List<BatchStockModel> batchList = new ArrayList<>();
        BatchStockModel batch = new BatchStockModel(1L, batchNumber, quantity, manufacturingDate, manufacturingTime, dueTime, product, null);
        batchList.add(batch);

        LocalDate time = LocalDate.now();
        String orderStatus = "";
        List<OrderProductsRequest> products = new ArrayList<>();
        products.add(new OrderProductsRequest());

        PurchaseOrderModel purchaseOrder = new PurchaseOrderModel();
        purchaseOrder.setDate(time);
        purchaseOrder.setOrderStatus(orderStatus);
        purchaseOrder.setBuyer(buyer);

        Mockito.when(buyerRepository.getReferenceById(ArgumentMatchers.anyLong()))
                .thenReturn(buyer);

        Mockito.when(buyerRepository.getReferenceById(ArgumentMatchers.anyLong()))
                .thenReturn(buyer);

        Mockito.when(
                        batchStockRepository.findBatchStockModelsByProductIdAndDueDateGreaterThanEqual(
                                ArgumentMatchers.any(), ArgumentMatchers.any()
                        )
                )
                .thenReturn(batchList);


    }
}