package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.PurchaseOrderModel;
import com.meli.frescos.repository.OrderProductsRepository;
import com.meli.frescos.repository.ProductRepository;
import com.meli.frescos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     *
     * @return List of OrderProductsModel
     */
    @Override
    public List<OrderProductsModel> getAll() {
        return orderProductsRepository.findAll();
    }

    /**
     * Save a new OrderProducts at storage
     *
     * @param orderProductsRequest the new OrderProducts to store
     * @return the new created OrderProducts
     */
    @Override
    public OrderProductsModel save(OrderProductsRequest orderProductsRequest) {
        Optional<ProductModel> product = productRepository.findById(orderProductsRequest.getProductModel());
        Optional<PurchaseOrderModel> purchaseOrder = purchaseOrderRepository.findById(orderProductsRequest.getPurchaseOrderModel());

        if (product.isEmpty()) {
            throw new NullPointerException("Product_id not found");
        }

        OrderProductsModel model = new OrderProductsModel(
                product.get(),
                orderProductsRequest.getQuantity(),
                purchaseOrder.get());
        return orderProductsRepository.save(model);
    }

    public List<OrderProductsModel> getByPurchaseId(Long purchaseId) throws Exception {
        PurchaseOrderModel purchaseOrderModel = purchaseOrderRepository.findById(purchaseId).orElseThrow(() -> new Exception("Purchase order not found"));
        List<OrderProductsModel> orderProductsModels = orderProductsRepository.findByPurchaseOrderModel_Id(purchaseOrderModel.getId());

        return orderProductsModels;
    }

    /**
     * Return OrderProductsModel given id
     *
     * @param id the OrderProductsModel id
     * @return OrderProductsModel
     */
    @Override
    public OrderProductsModel getById(Long id) throws Exception {
        return orderProductsRepository.findById(id).orElseThrow(() -> new Exception("OrderProduct not found"));
    }
}
