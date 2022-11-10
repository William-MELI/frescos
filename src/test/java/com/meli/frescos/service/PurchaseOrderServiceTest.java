package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.BatchStockRepository;
import com.meli.frescos.repository.BuyerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceTest {

    @InjectMocks
    PurchaseOrderService purchaseOrderService;

    @Mock
    BuyerRepository buyerRepository;

    @Mock
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

        PurchaseOrderRequest request = PurchaseOrderRequest.builder()
                .orderStatus(orderStatus)
                .buyer(buyer.getId())
                .date(time)
                .products(products)
                .build();

        Mockito.when(buyerRepository.getReferenceById(ArgumentMatchers.any()))
                .thenReturn(buyer);

        Mockito.when(buyerRepository.getReferenceById(ArgumentMatchers.any()))
                .thenReturn(buyer);

//        Mockito.when(
//                        batchStockRepository.findBatchStockModelsByProductIdAndDueDateGreaterThanEqual(
//                                ArgumentMatchers.any(), ArgumentMatchers.any()
//                        )
//                )
//                .thenReturn(batchList);

        purchaseOrderService.save(request);

    }
}