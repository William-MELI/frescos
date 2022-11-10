package com.meli.frescos.service;

import com.meli.frescos.controller.dto.OrderProductsRequest;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.repository.OrderProductsRepository;
import com.meli.frescos.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductService implements IOrderProductService{

    private final OrderProductsRepository repo;

    private final ProductRepository productRepo;

    public OrderProductService(OrderProductsRepository repo, ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
    }

    @Override
    public List<OrderProductsModel> getAll() {
        return repo.findAll();
    }

    @Override
    public OrderProductsModel save(OrderProductsRequest orderProductsRequest) {
        Optional<ProductModel> product = productRepo.findById(orderProductsRequest.getProductModel());
        if (product.isEmpty()) {
            throw new NullPointerException("Product_id not found");
        }

        OrderProductsModel model = new OrderProductsModel(
                product.get(),
                orderProductsRequest.getQuantity());
        return repo.save(model);
    }
}
