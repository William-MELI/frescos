package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.exception.OrderProductIsInvalidException;
import com.meli.frescos.exception.ProductByIdNotFoundException;
import com.meli.frescos.exception.PurchaseOrderByIdNotFoundException;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.repository.OrderProductsRepository;
import com.meli.frescos.repository.ProductRepository;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class contains all OrderProducts related functions
 * Using @Service from spring
 */
@Service
public class OrderProductService implements IOrderProductService {

    private final OrderProductsRepository orderProductsRepository;

    private final ProductRepository productRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;


    public OrderProductService(OrderProductsRepository orderProductsRepository, ProductRepository productRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.orderProductsRepository = orderProductsRepository;
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    /**
     * Return all OrderProducts
     * @return List of OrderProductsModel
     */
    @Override
    public List<OrderProductsModel> getAll() {
        return orderProductsRepository.findAll();
    }

    /**
     * Save a new OrderProducts at storage
     * @param orderProductsRequest the new OrderProducts to store
     * @return the new created OrderProducts
     * @throws ProductByIdNotFoundException Throws in case Product does not exists
     * @throws PurchaseOrderByIdNotFoundException Throws in case Ptroduct does not exists
     */
    @Override
    public OrderProductsModel save(OrderProductsRequest orderProductsRequest) throws ProductByIdNotFoundException, PurchaseOrderByIdNotFoundException {
        ProductModel product = productRepository.findById(orderProductsRequest.getProductModel())
                .orElseThrow(() -> new ProductByIdNotFoundException(orderProductsRequest.getProductModel()));

        PurchaseOrderModel purchaseOrder = purchaseOrderRepository.findById(orderProductsRequest.getPurchaseOrderModel())
                .orElseThrow(() -> new PurchaseOrderByIdNotFoundException(orderProductsRequest.getPurchaseOrderModel()));

        OrderProductsModel model = new OrderProductsModel(
                product,
                orderProductsRequest.getQuantity(),
                purchaseOrder);
        return orderProductsRepository.save(model);
    }

    /**
     * Return PurchaseOrderModel given id
     * @param purchaseId the PurchaseOrderModel id
     * @return List of OrderProductsModel
     */
    @Override
    public List<OrderProductsModel> getByPurchaseId(Long purchaseId) throws PurchaseOrderByIdNotFoundException {
        PurchaseOrderModel purchaseOrderModel = purchaseOrderRepository.findById(purchaseId)
                .orElseThrow(() -> new PurchaseOrderByIdNotFoundException(purchaseId));

        List<OrderProductsModel> orderProductsModels = orderProductsRepository.findByPurchaseOrderModel(purchaseOrderModel);

        return orderProductsModels;
    }

    /**
     * Return OrderProductsModel given id
     * @param id the OrderProductsModel id
     * @return OrderProductsModel
     * @throws OrderProductIsInvalidException Throws in case OrderProduct does not exists
     */
    @Override
    public OrderProductsModel getById(Long id) throws OrderProductIsInvalidException {
        return orderProductsRepository.findById(id)
                .orElseThrow(() -> new OrderProductIsInvalidException("Pedido de compra inválido"));
    }
}
