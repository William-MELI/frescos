package com.meli.frescos.service;

import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.ProductRepository;
import org.springframework.stereotype.Service;
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
    public ProductModel getById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception("Product not found."));
    }

    @Override
    public ProductModel save(ProductModel product, Long sellerCode) {
        SellerModel savingSeller = iSellerService.findById(sellerCode);
        product.setSeller(savingSeller);
        return productRepository.save(product);
    }

    @Override
    public List<ProductModel> getByCategory(String filter) {
        int type = -1;

        if(filter.equalsIgnoreCase("FS"))
            type = 0;

        if(filter.equalsIgnoreCase("FF"))
            type = 1;

        if(filter.equalsIgnoreCase("RF"))
            type = 2;

        return productRepository.getProductByCategory(type);
    }

}
