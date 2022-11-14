package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockFilterOrderInvalidException;
import com.meli.frescos.model.*;
import com.meli.frescos.repository.BatchStockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BatchStockServiceTest {

    @InjectMocks
    BatchStockService batchStockService;

    @Mock
    BatchStockRepository batchStockRepository;

    @Mock
    IProductService productService;

    private SellerModel seller;
    private ProductModel product;
    private WarehouseModel warehouse;
    private SectionModel section;

    void createAttributesBatchStock(){
        seller = new SellerModel(1L, "Tadeu", "123456789-00", 5.0);
        product = new ProductModel(1L, "Melão", "Melão", new BigDecimal(4.5), CategoryEnum.FRESH, 10.0, 10.0, LocalDate.now(), seller);
        warehouse = new WarehouseModel(1L, "São Paulo", "SP", "São Paulo", "Rua A", "11111-111");
        section = new SectionModel(1L, "Sessão Frutas", CategoryEnum.FRESH, 200.0, 25.0, warehouse);
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void save() {
    }

    @Test
    @DisplayName("Return a list batch stock by productId")
    void getByProductId_returnProduct_whenSucess() {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);
        BDDMockito.when(batchStockRepository.findByProduct(ArgumentMatchers.any(ProductModel.class)))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getByProductId(1L);

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest).isEqualTo(batchStockList);
    }

    @Test
    void findBySectionId() {
    }

    @Test
    void getTotalBatchStockQuantity() {
    }

    @Test
    void isValid() {
    }

    @Test
    @DisplayName("Return a list batch stock sorted by batch")
    void getByProductOrder_returnOrderBatchStock_whenSuccess() {
        createAttributesBatchStock();
        BatchStockModel batchStock1 = new BatchStockModel(1L, "456DEF", 20, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "123ABC", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);

        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        BDDMockito.when(batchStockRepository.findProducts(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getByProductOrder(1L, "L");

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest.get(0)).isEqualTo(batchStock2);
    }

    @Test
    @DisplayName("Return a list batch stock sorted by quantity product")
    void getByProductOrder_returnOrderQuantity_whenSuccess() {
        createAttributesBatchStock();
        BatchStockModel batchStock1 = new BatchStockModel(1L, "456DEF", 20, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "123ABC", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);

        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        BDDMockito.when(batchStockRepository.findProducts(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getByProductOrder(1L, "Q");

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest.get(0)).isEqualTo(batchStock1);
    }

    @Test
    @DisplayName("Return a list batch stock sorted by due date")
    void getByProductOrder_returnOrderDueDate_whenSuccess() {
        createAttributesBatchStock();
        BatchStockModel batchStock1 = new BatchStockModel(1L, "456DEF", 20, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "123ABC", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2022,12,15), product, section);

        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        BDDMockito.when(batchStockRepository.findProducts(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getByProductOrder(1L, "V");

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest.get(0)).isEqualTo(batchStock2);
    }

    @Test
    @DisplayName("Return exception BatchStockFilterOrderInvalidException when invalid order")
    void getByProductOrder_returnBatchStockFilterOrderInvalidException_whenInvalidOrder() {
        assertThrows(BatchStockFilterOrderInvalidException.class, () -> {
            List<BatchStockModel> batchStockModelList = batchStockService.getByProductOrder(1L, "F");
        });
    }
}
