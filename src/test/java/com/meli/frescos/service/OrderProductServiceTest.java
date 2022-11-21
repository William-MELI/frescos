package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.repository.OrderProductsRepository;
import com.meli.frescos.repository.ProductRepository;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceTest {

    @InjectMocks
    OrderProductService orderProductService;

    @Mock
    OrderProductsRepository orderProductsRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    PurchaseOrderRepository purchaseOrderRepository;


    @Test
    @DisplayName("Test OrderProductService save method with valid parameters")
    void save_returnOrderProductModel_whenSuccess() {
        OrderProductsRequest request = OrderProductsRequest.builder()
                .productModel(1L)
                .purchaseOrderModel(1L)
                .quantity(1)
                .build();

        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setId(1L);
        ProductModel productModel = new ProductModel();
        productModel.setId(1L);

        OrderProductsModel orderProductsModel = new OrderProductsModel();
        orderProductsModel.setQuantity(1);
        orderProductsModel.setPurchaseOrderModel(purchaseOrderModel);
        orderProductsModel.setProductModel(productModel);

        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(productModel));

        BDDMockito.when(purchaseOrderRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(purchaseOrderModel));

        BDDMockito.when(orderProductsRepository.save(ArgumentMatchers.any()))
                .thenReturn(orderProductsModel);


        OrderProductsModel newOrderProduct = orderProductService.save(request);

        assertEquals(newOrderProduct.getQuantity(), 1);
        assertEquals(newOrderProduct.getProductModel().getId(), 1L);
        assertEquals(newOrderProduct.getPurchaseOrderModel().getId(), 1L);

    }

    @Test
    @DisplayName("Test OrderProductService save method with inexistent ProductModel")
    void save_throwsNullPointerException_whenProductDoesNotExists() {
        OrderProductsRequest request = OrderProductsRequest.builder()
                .productModel(1L)
                .purchaseOrderModel(1L)
                .quantity(1)
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.empty());
        BDDMockito.when(purchaseOrderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> orderProductService.save(request));

    }

    @Test
    @DisplayName("Test OrderProductService getAll method with mocked list")
    void getAll_returnListOfOrderProductModel_whenSuccess() {
        List<OrderProductsModel> listOrderProduct = new ArrayList<OrderProductsModel>();

        listOrderProduct.add(new OrderProductsModel(1L, null, 1, null));
        listOrderProduct.add(new OrderProductsModel(2L, null, 1, null));

        BDDMockito.when(orderProductsRepository.findAll())
                .thenReturn(listOrderProduct);

        List<OrderProductsModel> returnListOrder = orderProductService.getAll();

        assertEquals(returnListOrder.size(), 2);
        assertEquals(returnListOrder.get(0).getId(), 1L);
        assertEquals(returnListOrder.get(1).getId(), 2L);

    }

    @Test
    @DisplayName("Test OrderProductService getByPurchaseId method with valid parameters")
    void getByPurchaseId_returnOrderProductModel_whenSuccess() throws Exception {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setId(1L);

        List<OrderProductsModel> listOrderProduct = new ArrayList<OrderProductsModel>();
        listOrderProduct.add(new OrderProductsModel(1L, null, 1, null));

        BDDMockito.when(purchaseOrderRepository.findById(1L))
                .thenReturn(Optional.of(purchaseOrderModel));

        BDDMockito.when(orderProductsRepository.findByPurchaseOrderModel_Id(purchaseOrderModel.getId()))
                .thenReturn(listOrderProduct);

        List<OrderProductsModel> returnListOrder = orderProductService.getByPurchaseId(purchaseOrderModel.getId());

        assertEquals(returnListOrder.size(), 1);
        assertEquals(returnListOrder.get(0).getId(), 1L);

    }

    @Test
    @DisplayName("Returns a OrderProduct from Storage.")
    void getById_returnsOrderProductModel_WhenSuccess() throws Exception {
        PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
        purchaseOrderModel.setId(1L);
        ProductModel productModel = new ProductModel();
        productModel.setId(1L);

        OrderProductsModel orderProductsModel = new OrderProductsModel();
        orderProductsModel.setQuantity(1);
        orderProductsModel.setId(1L);
        orderProductsModel.setPurchaseOrderModel(purchaseOrderModel);
        orderProductsModel.setProductModel(productModel);

        BDDMockito.when(orderProductsRepository.findById(1L))
                .thenReturn(Optional.of(orderProductsModel));

        OrderProductsModel returnOrderProduct = orderProductService.getById(1L);

        assertEquals(1L, returnOrderProduct.getId());
        assertEquals(1, returnOrderProduct.getQuantity());
        assertEquals(1L, returnOrderProduct.getProductModel().getId());

    }

    @Test
    @DisplayName("Test OrderProductService findById with valid parameters")
    void getByPurchaseId_throwsException_whenPurchaseOrderDoesNotExists() throws Exception {


        BDDMockito.when(purchaseOrderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> orderProductService.getByPurchaseId(1L));
    }


    @Test
    @DisplayName("Throw exception when ID is not found.")
    void getById_throwsException_WhenIdIsInvalid() {

        Mockito.when(orderProductsRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> orderProductService.getById(1L));

    }
}