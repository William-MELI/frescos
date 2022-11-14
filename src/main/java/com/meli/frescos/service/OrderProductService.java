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
 *  This class contains all OrderProducts related functions
 *  Using @Service from spring
 */
@Service
public class OrderProductService implements IOrderProductService {

    private final OrderProductsRepository repo;

    private final ProductRepository productRepo;

    private final PurchaseOrderRepository purchaseOrderRepo;


    public OrderProductService(OrderProductsRepository repo, ProductRepository productRepo, PurchaseOrderRepository purchaseOrderRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.purchaseOrderRepo = purchaseOrderRepo;
    }

    /**
     * Return all OrderProducts
     * @return List of OrderProductsModel
     */
    @Override
    public List<OrderProductsModel> getAll() {
        return repo.findAll();
    }

    /**
     * Save a new OrderProducts at storage
     *
     * @param orderProductsRequest the new OrderProducts to store
     * @return the new created OrderProducts
     */
    @Override
    public OrderProductsModel save(OrderProductsRequest orderProductsRequest) {
        Optional<ProductModel> product = productRepo.findById(orderProductsRequest.getProductModel());
        Optional<PurchaseOrderModel> purchaseOrder = purchaseOrderRepo.findById(orderProductsRequest.getPurchaseOrderModel());

        if (product.isEmpty()) {
            throw new NullPointerException("Product_id not found");
        }

        OrderProductsModel model = new OrderProductsModel(
                product.get(),
                orderProductsRequest.getQuantity(),
                purchaseOrder.get());
        return repo.save(model);
    }

    public List<OrderProductsModel> getByPurchaseId(Long purchaseId) throws Exception {
        PurchaseOrderModel purchaseOrderModel = purchaseOrderRepo.findById(purchaseId).orElseThrow(() -> new Exception("Purchase order not found"));
        List<OrderProductsModel> orderProductsModels = repo.findByPurchaseOrderModel_Id(purchaseOrderModel.getId());

        return orderProductsModels;
    }

    /**
     * Return OrderProductsModel given id
     * @param id the OrderProductsModel id
     * @return OrderProductsModel
     */
    @Override
    public OrderProductsModel getById(Long id) throws Exception {
        return repo.findById(id).orElseThrow(() -> new Exception("OrderProduct not found"));
    }
}
