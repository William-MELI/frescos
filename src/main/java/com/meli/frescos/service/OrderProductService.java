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

    @Override
    public List<OrderProductsModel> getAll() {
        return repo.findAll();
    }

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

    public List<OrderProductsModel> getByPurchaseId(Long purchaseId) {
        PurchaseOrderModel purchaseOrderModel = purchaseOrderRepo.getById(purchaseId);
        List<OrderProductsModel> orderProductsModels = repo.findByPurchaseOrderModel(purchaseOrderModel);

        return orderProductsModels;
    }

    @Override
    public OrderProductsModel getById(Long id) throws Exception {
        return repo.findById(id).orElseThrow(() -> new Exception("OrderProduct not found"));
    }
}
