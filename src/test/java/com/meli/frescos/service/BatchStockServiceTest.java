package com.meli.frescos.service;

import com.meli.frescos.model.BatchStockModel;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.ProductModel;
import com.meli.frescos.repository.BatchStockRepository;
import com.meli.frescos.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BatchStockServiceTest {


    @InjectMocks
    BatchStockService batchStockService;

    @Mock
    ProductService productService;
    @Mock
    SectionService sectionService;
    @Mock
    SellerService sellerService;
    @Mock
    RepresentativeService representativeService;
    @Mock
    ProductRepository productRepository;
    @Mock
    BatchStockRepository batchStockRepository;

    @Test
    void findValidProductsByDueDate_returnListOfBatchStock_whenSuccess() throws Exception {
        String batchNumber = "L-123";
        int quantity = 10;
        LocalDate manufacturingDate = LocalDate.now().minusWeeks(10);
        LocalDateTime manufacturingTime = LocalDateTime.now();
        LocalDate dueTime = LocalDate.now().minusWeeks(10);

        ProductModel product = new ProductModel(1L,
                "Camiseta",
                "Test",
                new BigDecimal(10),
                CategoryEnum.FROZEN,
                1.0,
                1.0,
                LocalDate.now(),
                null);

        List<BatchStockModel> batchList = new ArrayList<>();
        BatchStockModel batch = new BatchStockModel(1L, batchNumber, quantity, manufacturingDate, manufacturingTime, dueTime, product, null);
        batchList.add(batch);

        Mockito.when(sectionService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Mockito.when(sellerService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Mockito.when(representativeService.permittedRepresentative(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(true);

        ProductModel pmodel = productService.save(product, 0L);
        batchStockService.save(batch, product.getId(), 0L, 0L, 0L);

        List<ProductModel> listP = productService.getAll();
        System.out.println(listP.size());
        LocalDate timeToCompare = LocalDate.now();

        List<BatchStockModel> batches = batchStockService.findValidProductsByDueDate(product.getId(), timeToCompare);
//        List<BatchStockModel> batches2 = batchStockService.findByProductId(product.getId());
        System.out.println(batches.size());

    }
}
