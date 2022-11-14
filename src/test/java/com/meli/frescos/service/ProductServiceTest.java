package com.meli.frescos.service;

import com.meli.frescos.exception.ProductByIdNotFoundException;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private ISellerService sellerService;

    @Test
    @DisplayName("Return all storage Product")
    void getAll_returnAllProducts_whenSuccess() {
        SellerModel seller = new SellerModel(1L, "Afonso", "123.456.789-00", 4.5);
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.FRESH, 5.0, 5.0, LocalDate.now(), seller));

        BDDMockito.when(repository.findAll())
                .thenReturn(products);

        List<ProductModel> productsTest = service.getAll();

        assertThat(productsTest).isNotNull();
        assertThat(productsTest).isEqualTo(products);
    }

    @Test
    @DisplayName("Return a Product by id")
    void getById_returnProduct_whenSucess() {
        SellerModel seller = new SellerModel(1L, "Afonso", "123.456.789-00", 4.5);
        ProductModel product = new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.FRESH, 5.0, 5.0, LocalDate.now(), seller);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        ProductModel productTest = service.getById(1L);

        assertThat(productTest).isNotNull();
        assertThat(productTest).isEqualTo(product);
    }

    @Test
    @DisplayName("Throw exception when ID is not found.")
    void getById_returnProductByIdNotFoundException_whenInvalidId() {
        assertThrows(ProductByIdNotFoundException.class, () -> {
            ProductModel product = service.getById(ArgumentMatchers.anyLong());
        });
    }

    @Test
    @DisplayName("Create a new Product successfully")
    void saveProduct_returnsCreatedProduct_whenSuccess() {
        SellerModel seller = new SellerModel(1L, "Afonso", "123.456.789-00", 4.5);
        ProductModel product = new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.FRESH, 5.0, 5.0, LocalDate.now(), seller);

        BDDMockito.when(sellerService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(seller);
        BDDMockito.when(repository.save(ArgumentMatchers.any(ProductModel.class)))
                .thenReturn(product);

        ProductModel productTest = service.save(new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.FRESH, 5.0, 5.0, LocalDate.now(), seller));

        assertThat(productTest).isNotNull();
        assertThat(productTest.getId()).isPositive();
        assertEquals(product.getProductTitle(), productTest.getProductTitle());
    }

    @Test
    @DisplayName("Return all storage Product by category fresh")
    void getByCategory_returnListProductsFresh_whenSucess() {
        SellerModel seller = new SellerModel(1L, "Afonso", "123.456.789-00", 4.5);
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.FRESH, 5.0, 5.0, LocalDate.now(), seller));

        BDDMockito.when(repository.findByCategory(ArgumentMatchers.any()))
                .thenReturn(products);

        List<ProductModel> productTest = service.getByCategory("FS");

        assertThat(productTest).isNotNull();
        assertThat(productTest.get(0).getCategory()).isEqualTo(CategoryEnum.FRESH);
    }

    @Test
    @DisplayName("Return all storage Product by category frozen")
    void getByCategory_returnListProductsFrozen_whenSucess() {
        SellerModel seller = new SellerModel(1L, "Afonso", "123.456.789-00", 4.5);
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.FROZEN, 5.0, 5.0, LocalDate.now(), seller));

        BDDMockito.when(repository.findByCategory(ArgumentMatchers.any()))
                .thenReturn(products);

        List<ProductModel> productTest = service.getByCategory("FF");

        assertThat(productTest).isNotNull();
        assertThat(productTest.get(0).getCategory()).isEqualTo(CategoryEnum.FROZEN);
    }

    @Test
    @DisplayName("Return all storage Product by category refrigerated")
    void getByCategory_returnListProductsRefrigerated_whenSucess() {
        SellerModel seller = new SellerModel(1L, "Afonso", "123.456.789-00", 4.5);
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel(1L, "Manga", "Manga Tommy", new BigDecimal(2.99), CategoryEnum.REFRIGERATED, 5.0, 5.0, LocalDate.now(), seller));

        BDDMockito.when(repository.findByCategory(ArgumentMatchers.any()))
                .thenReturn(products);

        List<ProductModel> productTest = service.getByCategory("RF");

        assertThat(productTest).isNotNull();
        assertThat(productTest.get(0).getCategory()).isEqualTo(CategoryEnum.REFRIGERATED);
    }

    @Test
    @DisplayName("Return list null when category is not found")
    void getByCategory_returnEmpty_whenNotFound() {

        BDDMockito.when(repository.findByCategory(ArgumentMatchers.any()))
                .thenReturn(null);

        List<ProductModel> productTest = service.getByCategory("RF");

        assertThat(productTest).isNull();
    }

}