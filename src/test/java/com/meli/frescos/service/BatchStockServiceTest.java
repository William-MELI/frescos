package com.meli.frescos.service;

import com.meli.frescos.exception.BatchStockByIdNotFoundException;
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
import java.util.Optional;
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

    @Mock
    ISectionService sectionService;

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
    @DisplayName("Return all storage BatchStock")
    void getAll_returnAllBatchStock_whenSuccess() {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        BDDMockito.when(batchStockRepository.findAll())
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getAll();

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest).isEqualTo(batchStockList);
    }

    @Test
    @DisplayName("Return a BatchStock by id")
    void getById_returnBatchStock_whenSucess() {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);

        BDDMockito.when(batchStockRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(batchStock));

        BatchStockModel batchStockTest = batchStockService.getById(1L);

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest.getId()).isEqualTo(batchStock.getId());
    }

    @Test
    @DisplayName("Throw exception when ID is not found.")
    void getById_returnBatchStockByIdNotFoundException_whenInvalidId() {
        assertThrows(BatchStockByIdNotFoundException.class, () -> {
            BatchStockModel batchStock = batchStockService.getById(ArgumentMatchers.anyLong());
        });
    }

    @Test
    @DisplayName("Create a new BatchStock successfully")
    void saveBatchStock_returnsCreatedBatchStock_whenSuccess() throws Exception {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);

        BDDMockito.when(sectionService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(section);
        BDDMockito.when(batchStockRepository.save(ArgumentMatchers.any(BatchStockModel.class)))
                .thenReturn(batchStock);

        BatchStockModel batchStockTest = batchStockService.save(batchStock);

        assertThat(batchStockTest.getId()).isEqualTo(batchStock.getId());
    }

    @Test
    @DisplayName("Return a list batch stock by product ID")
    void getByProductId_returnListBatchStock_whenSucess() {
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
    @DisplayName("Return a list batch stock by section ID")
    void getBySectionId_returnListBatchStock_whenSuccess() throws Exception {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        BDDMockito.when(sectionService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(section);
        BDDMockito.when(batchStockRepository.findBySection(ArgumentMatchers.any(SectionModel.class)))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getBySectionId(1L);

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest).isEqualTo(batchStockList);
    }

    @Test
    @DisplayName("Return a list batch stock by section ID and due date")
    void getBySectionIdAndDueDate_returnListBatchStock_whenDueDateNotExpired() throws Exception {
        createAttributesBatchStock();
        int plusDays = 10;
        LocalDate dueDate = LocalDate.now().plusDays(plusDays);
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), dueDate, product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        BDDMockito.when(sectionService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(section);
        BDDMockito.when(batchStockRepository.findBySectionAndDueDateBetween(ArgumentMatchers.any(SectionModel.class), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getBySectionIdAndDueDate(1L, plusDays);

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest).isEqualTo(batchStockList);
    }

    @Test
    @DisplayName("Return a list batch stock empty by section ID and due date expired")
    void getBySectionIdAndDueDate_returnListEmpty_whenDueDateIsExpired() throws Exception {
        createAttributesBatchStock();

        BDDMockito.when(sectionService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(section);
        BDDMockito.when(batchStockRepository.findBySectionAndDueDateBetween(ArgumentMatchers.any(SectionModel.class), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());

        List<BatchStockModel> batchStockTest = batchStockService.getBySectionIdAndDueDate(1L, 15);

        assertThat(batchStockTest).isEmpty();
    }

    @Test
    @DisplayName("Return a list batch stock by Category and due date")
    void getByCategoryAndDueDate_returnListBatchStock_whenCategoryExistsAndDueDateNotExpired() throws Exception {
        createAttributesBatchStock();

        List<SectionModel> sectionList = new ArrayList<>();
        sectionList.add(section);

        int plusDays = 10;
        LocalDate dueDate = LocalDate.now().plusDays(plusDays);
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), dueDate, product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);

        BDDMockito.when(sectionService.getByCategory(ArgumentMatchers.any()))
                .thenReturn(sectionList);

        BDDMockito.when(batchStockRepository.findBySectionAndDueDateBetween(ArgumentMatchers.any(SectionModel.class), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.getByCategoryAndDueDate(CategoryEnum.FRESH, 5);

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest).isEqualTo(batchStockList);
    }

    @Test
    @DisplayName("Return a list batch stock empty when Category not exists or due date expired")
    void getByCategoryAndDueDate_returnListBatchStock_whenCategoryNotExistsOrDueDateExpired() throws Exception {
        createAttributesBatchStock();

        List<SectionModel> sectionList = new ArrayList<>();
        sectionList.add(section);

        BDDMockito.when(sectionService.getByCategory(ArgumentMatchers.any()))
                .thenReturn(sectionList);

        BDDMockito.when(batchStockRepository.findBySectionAndDueDateBetween(ArgumentMatchers.any(SectionModel.class), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());

        List<BatchStockModel> batchStockTest = batchStockService.getByCategoryAndDueDate(CategoryEnum.FRESH, 10);

        assertThat(batchStockTest).isEmpty();
    }

    @Test
    @DisplayName("Return the sum quantity of products by product ID")
    void getTotalBatchStockQuantity_returnCorrectQuantity_whenSuccess() throws Exception {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "ABC123", 100, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);
        batchStockList.add(batchStock2);
        int totalQuantity = 150;

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);
        BDDMockito.when(batchStockService.getByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(batchStockList);

        int totalQuantityTest = batchStockService.getTotalBatchStockQuantity(1L);

        assertThat(totalQuantityTest).isPositive();
        assertThat(totalQuantityTest).isEqualTo(totalQuantity);
    }

    @Test
    @DisplayName("Return the closest due date of product by product ID")
    void getClosestDueDate_returnClosestDueDate_whenSuccess() throws Exception {
        createAttributesBatchStock();
        LocalDate dueDate1 = LocalDate.now().plusDays(30);
        LocalDate dueDate2 = LocalDate.now().plusDays(20);

        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), dueDate1, product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "ABC123", 100, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), dueDate2, product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);
        batchStockList.add(batchStock2);

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);
        BDDMockito.when(batchStockService.getByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(batchStockList);

        LocalDate dueDateTest = batchStockService.getClosestDueDate(1L);

        assertThat(dueDateTest).isNotNull();
        assertThat(dueDateTest).isEqualTo(dueDate2);
    }

    /* Para terminar os testes desse método é necessário fazer mock de métodos que estão privados
    @Test
    @DisplayName("Not return exceptions when success")
    void validateBatches_notReturnException_whenSuccess() throws Exception {
        createAttributesBatchStock();
        BatchStockModel batchStock = new BatchStockModel(1L, "ABC123", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "ABC123", 100, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), LocalDate.of(2023,01,15), product, section);
        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock);
        batchStockList.add(batchStock2);

        BDDMockito.when(sectionService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(section);

        Assertions.assertThatCode(() -> batchStockService.validateBatches(product, batchStockList))
                .doesNotThrowAnyException();
    }*/

    @Test
    @DisplayName("Return a list BatchStock by due date valid of products")
    void findValidProductsByDueDate_returnListBatchStock_whenSuccess() {
        createAttributesBatchStock();
        LocalDate dueDate = LocalDate.now();
        BatchStockModel batchStock1 = new BatchStockModel(1L, "456DEF", 20, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), dueDate, product, section);
        BatchStockModel batchStock2 = new BatchStockModel(1L, "123ABC", 50, LocalDate.of(2022,10,10), LocalDateTime.of(2022,10,10,15,00), dueDate, product, section);

        List<BatchStockModel> batchStockList = new ArrayList<>();
        batchStockList.add(batchStock1);
        batchStockList.add(batchStock2);

        BDDMockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        BDDMockito.when(batchStockRepository.findProducts(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(batchStockList);

        List<BatchStockModel> batchStockTest = batchStockService.findValidProductsByDueDate(1L, dueDate);

        assertThat(batchStockTest).isNotNull();
        assertThat(batchStockTest).isEqualTo(batchStockList);
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
