package com.meli.frescos.service;

import com.meli.frescos.exception.ProductByIdNotFoundException;
import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final ISellerService iSellerService;

    public ProductService(ProductRepository productRepository, ISellerService iSellerService) {
        this.productRepository = productRepository;
        this.iSellerService = iSellerService;
    }

    @Override
    public List<ProductModel> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductModel getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductByIdNotFoundException(id));
    }

    @Override
    public ProductModel save(ProductModel product) {
        product.setSeller(iSellerService.getById(product.getSeller().getId()));
        return productRepository.save(product);
    }

    @Override
    public List<ProductModel> getByCategory(String filter) {
        return switch (filter.toUpperCase()) {
            case "FS" -> productRepository.findByCategory(CategoryEnum.FRESH);
            case "FF" -> productRepository.findByCategory(CategoryEnum.FROZEN);
            case "RF" -> productRepository.findByCategory(CategoryEnum.REFRIGERATED);
            default -> new ArrayList<>();
        };
    }

    @Override
    public ProductModel save(ProductModel product, List<BatchStockModel> batchStockList) throws Exception {
        return save(product);
    }

}
