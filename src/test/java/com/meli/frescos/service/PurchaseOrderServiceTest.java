package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.controller.dto.PurchaseOrderRequest;
import com.meli.frescos.exception.OrderProductIsInvalidException;
import com.meli.frescos.exception.PurchaseOrderByIdNotFoundException;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceTest {

    @InjectMocks
    PurchaseOrderService purchaseOrderService;

    @Mock
    IBuyerService iBuyerService;

    @Mock
    IOrderProductService iOrderProductService;

    @Mock
    PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    IBatchStockService iBatchStockService;

    @Test
    @DisplayName("Create new purchase order when successfully")
    void save_returnPurchaseOrderModel_whenSuccess() {
        List<OrderProductsRequest> orderProductsRequests = new ArrayList<>();
        OrderProductsRequest orderProductsRequest = new OrderProductsRequest(1L, 10, 1L);
        orderProductsRequests.add(orderProductsRequest);
        orderProductsRequests.add(orderProductsRequest);

        PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest(LocalDate.now(), 1L, orderProductsRequests);

        BuyerModel buyerModel = new BuyerModel();
        buyerModel.setId(1L);
        buyerModel.setCpf("08392648609");
        buyerModel.setName("Joaozinho");

        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setDate(purchaseOrderRequest.getDate());
        purchaseOrderModel.setId(1L);
        purchaseOrderModel.setOrderStatus(OrderStatusEnum.OPEN);

        Mockito.when(iBuyerService.getById(purchaseOrderRequest.getBuyer())).thenReturn(buyerModel);
        Mockito.when(purchaseOrderRepository.save(ArgumentMatchers.any(PurchaseOrderModel.class))).thenReturn(purchaseOrderModel);

        PurchaseOrderModel responsePurchaseSave = purchaseOrderService.save(purchaseOrderRequest);

        assertEquals(purchaseOrderModel.getOrderStatus(), responsePurchaseSave.getOrderStatus());
        assertEquals(purchaseOrderModel.getDate(), responsePurchaseSave.getDate());


    }

    @Test
    @DisplayName("Create order products and purchase and return sum price orders when success")
    void savePurchasePrice_returnSumPriceOrders_whenSuccess() {
        OrderProductsRequest orderProductsRequest = new OrderProductsRequest(1L, 1, 1L);

        List<OrderProductsRequest> orderProductsRequests = new ArrayList<>();
        orderProductsRequests.add(orderProductsRequest);
        orderProductsRequests.add(orderProductsRequest);

        PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest(LocalDate.now(), 1L, orderProductsRequests);

        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setDate(purchaseOrderRequest.getDate());
        purchaseOrderModel.setId(1L);
        purchaseOrderModel.setOrderStatus(OrderStatusEnum.OPEN);

        ProductModel productModel = new ProductModel();
        productModel.setPrice(new BigDecimal(15));

        OrderProductsModel orderProductsModel = new OrderProductsModel();
        orderProductsModel.setId(1L);
        orderProductsModel.setPurchaseOrderModel(purchaseOrderModel);
        orderProductsModel.setQuantity(orderProductsRequest.getQuantity());
        orderProductsModel.setProductModel(productModel);

        BatchStockModel batchStockModel = new BatchStockModel();
        batchStockModel.setQuantity(200);

        List<BatchStockModel> batchStockModelList = new ArrayList<>();
        batchStockModelList.add(batchStockModel);
        batchStockModelList.add(batchStockModel);

        BuyerModel buyerModel = new BuyerModel();
        buyerModel.setId(1L);
        buyerModel.setCpf("08392648609");
        buyerModel.setName("Joaozinho");


        Mockito.when(iBuyerService.getById(purchaseOrderRequest.getBuyer())).thenReturn(buyerModel);

        Mockito.when(purchaseOrderRepository.save(ArgumentMatchers.any(PurchaseOrderModel.class))).thenReturn(purchaseOrderModel);

        Mockito.when(iBatchStockService.findValidProductsByDueDate(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(batchStockModelList);

        Mockito.when(iOrderProductService.save(ArgumentMatchers.any(OrderProductsRequest.class))).thenReturn(orderProductsModel);


        BigDecimal purchasePrice = purchaseOrderService.savePurchaseGetPrice(purchaseOrderRequest);

        BigDecimal result = new BigDecimal(30);

        assertEquals(result, purchasePrice);

    }

    @Test
    @DisplayName("Throw error when pass invalid quantity")
    void savePurchasePrice_throwOrderProductIsInvalidException_whenStockNotAvailable() {
        OrderProductsRequest orderProductsRequest = new OrderProductsRequest(1L, 50, 1L);

        List<OrderProductsRequest> orderProductsRequests = new ArrayList<>();
        orderProductsRequests.add(orderProductsRequest);
        orderProductsRequests.add(orderProductsRequest);

        PurchaseOrderRequest purchaseOrderRequest = new PurchaseOrderRequest(LocalDate.now(), 1L, orderProductsRequests);

        BatchStockModel batchStockModel = new BatchStockModel();
        batchStockModel.setQuantity(10);
        batchStockModel.setId(1L);

        List<BatchStockModel> batchStockModelList = new ArrayList<>();
        batchStockModelList.add(batchStockModel);
        batchStockModelList.add(batchStockModel);

        Mockito.when(iBatchStockService.findValidProductsByDueDate(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(batchStockModelList);

        assertThrows(OrderProductIsInvalidException.class, () -> {
            purchaseOrderService.savePurchaseGetPrice(purchaseOrderRequest);
        });
    }

    @Test
    @DisplayName("Get PurchaseOrder when pass Id")
    void getById_returnPurchaseOrderModel_whenSuccess() {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setDate(LocalDate.now());
        purchaseOrderModel.setId(1L);
        purchaseOrderModel.setOrderStatus(OrderStatusEnum.OPEN);

        Mockito.when(purchaseOrderRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(purchaseOrderModel));

        PurchaseOrderModel resultPurchaseOrderRequest = purchaseOrderService.getById(purchaseOrderModel.getId());

        assertEquals(purchaseOrderModel.getDate(), resultPurchaseOrderRequest.getDate());
        assertEquals(purchaseOrderModel.getOrderStatus(), resultPurchaseOrderRequest.getOrderStatus());

    }

    @Test
    @DisplayName("Throw error when pass wrong Id")
    void getById_throwPurchaseOrderByIdNotFoundException_whenNotFindId() {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setDate(LocalDate.now());
        purchaseOrderModel.setId(1L);
        purchaseOrderModel.setOrderStatus(OrderStatusEnum.OPEN);

        assertThrows(PurchaseOrderByIdNotFoundException.class, () -> {
            purchaseOrderService.getById(purchaseOrderModel.getId());
        });
    }

    @Test
    @DisplayName("Get all PurchaseOrder when success")
    void getAll_returnListPurchaseOrderModel_whenSuccess() {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setDate(LocalDate.now());
        purchaseOrderModel.setId(1L);
        purchaseOrderModel.setOrderStatus(OrderStatusEnum.OPEN);

        List<PurchaseOrderModel> purchaseOrderModelList = new ArrayList<>();
        purchaseOrderModelList.add(purchaseOrderModel);
        purchaseOrderModelList.add(purchaseOrderModel);

        Mockito.when(purchaseOrderRepository.findAll()).thenReturn(purchaseOrderModelList);

        List<PurchaseOrderModel> resultPurchaseOrderModels = purchaseOrderService.getAll();

        assertEquals(purchaseOrderModelList, resultPurchaseOrderModels);

    }

//    @Test
//    void updateStatus_returnVoid_whenSuccess() throws Exception {
//        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
//        purchaseOrderModel.setDate(LocalDate.now());
//        purchaseOrderModel.setId(1L);
//        purchaseOrderModel.setOrderStatus(OrderStatusEnum.OPEN);
//
//        ProductModel productModel = new ProductModel();
//        productModel.setPrice(new BigDecimal(15));
//
//        OrderProductsModel orderProductsModel = new OrderProductsModel();
//        orderProductsModel.setId(1L);
//        orderProductsModel.setPurchaseOrderModel(purchaseOrderModel);
//        orderProductsModel.setQuantity(10);
//        orderProductsModel.setProductModel(productModel);
//
//        List<OrderProductsModel> orderProductsModelList = new ArrayList<>();
//        orderProductsModelList.add(orderProductsModel);
//        orderProductsModelList.add(orderProductsModel);
//
//        BatchStockModel batchStockModel = new BatchStockModel();
//        batchStockModel.setQuantity(200);
//
//        List<BatchStockModel> batchStockModelList = new ArrayList<>();
//        batchStockModelList.add(batchStockModel);
//        batchStockModelList.add(batchStockModel);
//
//        Mockito.when(iOrderProductService.getByPurchaseId(ArgumentMatchers.anyLong())).thenReturn(orderProductsModelList);
//
//        Mockito.when(iBatchStockService.findValidProductsByDueDate(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(batchStockModelList);
//
//        Mockito.when(purchaseOrderRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(purchaseOrderModel));
//    }
}