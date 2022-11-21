package com.meli.frescos.service;

import com.meli.frescos.exception.ProductByIdNotFoundException;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class contains all Product related functions
 *  Using @Service from spring
 */
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final ISellerService iSellerService;

    public ProductService(ProductRepository productRepository, ISellerService iSellerService) {
        this.productRepository = productRepository;
        this.iSellerService = iSellerService;
    }

    /**
     * Return all ProductModel
     *
     * @return list of ProductModel
     */
    @Override
    public List<ProductModel> getAll() {
        return productRepository.findAll();
    }

    /**
     * Return ProductsModel given id
     *
     * @param id theProductModel id
     * @return ProductModel
     * @throws ProductByIdNotFoundException Throws in case Product does not exist
     */
    @Override
    public ProductModel getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductByIdNotFoundException(id));
    }

    /**
     * Save a new Product at storage
     *
     * @param product the new Product to store
     * @return the new created Product
     */
    @Override
    public ProductModel save(ProductModel product) {
        product.setSeller(iSellerService.getById(product.getSeller().getId()));
        return productRepository.save(product);
    }

    /**
     * Returns a list of ProductModel given a category
     *
     * @param filter category to search
     * @return list of ProductModel
     */
    @Override
    public List<ProductModel> getByCategory(String filter) {
        return switch (filter.toUpperCase()) {
            case "FS" -> productRepository.findByCategory(CategoryEnum.FRESH);
            case "FF" -> productRepository.findByCategory(CategoryEnum.FROZEN);
            case "RF" -> productRepository.findByCategory(CategoryEnum.REFRIGERATED);
            default -> new ArrayList<>();
        };
    }

}
