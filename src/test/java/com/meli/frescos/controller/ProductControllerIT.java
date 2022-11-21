package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.BatchStockRequest;
import com.meli.frescos.controller.dto.InboundOrderRequest;
import com.meli.frescos.controller.dto.ProductBatchStockRequest;
import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.exception.RepresentativeNotFoundException;
import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private com.meli.frescos.repository.ProductRepository productRepository;

    @Autowired
    private com.meli.frescos.repository.WarehouseRepository warehouseRepository;

    @Autowired
    private com.meli.frescos.repository.SellerRepository sellerRepository;

    @Autowired
    private com.meli.frescos.repository.RepresentativeRepository representativeRepository;

    @Autowired
    private com.meli.frescos.repository.SectionRepository sectionRepository;

    @Autowired
    private com.meli.frescos.repository.BatchStockRepository batchStockRepository;

    @AfterEach
    void setup() {
        this.batchStockRepository.deleteAllInBatch();
        this.productRepository.deleteAllInBatch();
        this.sellerRepository.deleteAllInBatch();
        this.representativeRepository.deleteAllInBatch();
        this.sectionRepository.deleteAllInBatch();
        this.warehouseRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("Test Product Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), sellerModel.getId(), representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);


        ResultActions response = mockMvc.perform(
                post("/product")
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.unitVolume", CoreMatchers.is(inboundOrderRequest.getUnitVolume())))
                .andExpect(jsonPath("$.unitWeight", CoreMatchers.is(inboundOrderRequest.getUnitWeight())))
                .andExpect(jsonPath("$.price", CoreMatchers.is(inboundOrderRequest.getPrice().doubleValue())))
                .andExpect(jsonPath("$.batchStock[0].batchNumber", CoreMatchers.is(inboundOrderRequest.getBatchStock().get(0).getBatchNumber())))
                .andExpect(jsonPath("$.batchStock[0].productQuantity", CoreMatchers.is(inboundOrderRequest.getBatchStock().get(0).getProductQuantity())))
                .andExpect(jsonPath("$.batchStock[0].manufacturingDate", CoreMatchers.containsString(inboundOrderRequest.getBatchStock().get(0).getManufacturingDate().toString())))
                .andExpect(jsonPath("$.batchStock[0].manufacturingTime", CoreMatchers.containsString(inboundOrderRequest.getBatchStock().get(0).getManufacturingDatetime().toString())))
                .andExpect(jsonPath("$.batchStock[0].dueDate", CoreMatchers.containsString(inboundOrderRequest.getBatchStock().get(0).getDueDate().toString())));
    }

    @Test
    @DisplayName("Test Product Create Throws WarehouseNotFoundException  - POST Endpoint")
    void create_throwsWarehouseNotFoundException_whenWarehouseCodeNull() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(-1L, sellerModel.getId(), representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);


        ResultActions response = mockMvc.perform(
                post("/product")
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Warehouse não encontrado")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Warehouse com ID -1 não encontrado")))
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof WarehouseNotFoundException));
    }

    @Test
    @DisplayName("Test Product Create Throws SellerByIdNotFoundException  - POST Endpoint")
    void create_throwsSellerByIdNotFoundException_whenSellerCodeInvalid() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), -1L, representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);


        ResultActions response = mockMvc.perform(
                post("/product")
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Vendedor não encontrado")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Vendedor com id -1 não encontrado")))
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof SellerByIdNotFoundException));
    }

    @Test
    @DisplayName("Test Product Create Throws RepresentativeNotFoundException  - POST Endpoint")
    void create_throwsRepresentativeNotFoundException_whenRepresentativeCodeInvalid() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);


        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), sellerModel.getId(), -1L, batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);


        ResultActions response = mockMvc.perform(
                post("/product")
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Representante não encontrado")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Representative not found.")))
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof RepresentativeNotFoundException));
    }

    @Test
    @DisplayName("Test Product Create Throws RepresentativeNotFoundException  - POST Endpoint")
    void create_throwsRepresentativeNotFoundException_whenWarehouseCodeNulsl() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), sellerModel.getId(), representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);


        ResultActions response = mockMvc.perform(
                post("/product")
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields", CoreMatchers.is("inboundOrder.batchStock")))
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.is("must not be empty")))
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
//    @Test
//    @DisplayName("Test invalid Buyer creation - POST Endpoint")
//    void create_throwException_whenWrongParameters() throws Exception {
//        String name = "";
//        String cpf = "";
//
//        BuyerRequest buyerRequest = BuyerRequest.builder()
//                .cpf(cpf)
//                .name(name)
//                .build();
//
//        ResultActions response = mockMvc.perform(
//                post("/buyer")
//                        .content(objectMapper.writeValueAsString(buyerRequest))
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        response.andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Test Get Warehouse by ID - GET Endpoint")
//    void getById_returnsWarehouse_whenIdIsAvailable() throws Exception {
//        String cpf = "12345678900";
//        String name = "Buyer";
//
//        BuyerRequest buyerRequest = BuyerRequest.builder()
//                .cpf(cpf)
//                .name(name)
//                .build();
//
//        BuyerModel buyer = buyerService.save(buyerRequest.toModel());
//
//        ResultActions response = mockMvc.perform(
//                get("/buyer/{id}", String.valueOf(buyer.getId()))
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        response.andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Test Warehouse ID not found - GET Endpoint")
//    void getById_throwsWarehouseNotFound_whenIdDoesNotExists() throws Exception {
//        ResultActions response = mockMvc.perform(
//                get("/buyer/{id}", -1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        response.andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("Test Get all Warehouse - GET Endpoint")
//    void getAll_returnListOfWarehouse_whenSuccess() throws Exception {
//        String name = "Buyer";
//        String cpf = "12345678900";
//
//        BuyerRequest buyerRequest = BuyerRequest.builder()
//                .cpf(cpf)
//                .name(name)
//                .build();
//
//        buyerService.save(buyerRequest.toModel());
//
//        ResultActions response = mockMvc.perform(
//                get("/buyer")
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        response.andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Test Update Buyer info - PUT Endpoint")
//    void update_returnsUpdatedBuyer_whenSuccess() throws Exception {
//        String cpf = "12345678900";
//        String name = "Buyer";
//        Long id;
//
//        BuyerRequest buyerRequest = BuyerRequest.builder()
//                .cpf(cpf)
//                .name(name)
//                .build();
//
//        id = buyerService.save(buyerRequest.toModel()).getId();
//
//        String newCpf = "95925955005";
//        String newName = "NewBuyer";
//
//        BuyerModel newBuyer =
//                BuyerRequest.builder()
//                        .cpf(newCpf)
//                        .name(newName)
//                        .build().toModel();
//
//        ResultActions response = mockMvc.perform(
//                put("/buyer/{id}", id)
//                        .content(objectMapper.writeValueAsString(newBuyer))
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        String expectedJson = String.format("{'name': '%s'}", newBuyer.getName());
//
//        response.andExpect(status().isOk())
//                .andExpect(content().json(expectedJson));
//    }

    @Test
    @DisplayName("Test Product GetAll Success - GET Endpoint")
    void getAll_returnsListOfProductDetailedResponse_whenSuccess() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), sellerModel.getId(), representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);

        ProductModel newProduct = productRepository.save(productBatchStockRequest.toProduct());
        List<BatchStockModel> productBatchStockList = productBatchStockRequest.toBatchStock();
        productBatchStockList.get(0).setProduct(newProduct);


        batchStockRepository.save(productBatchStockList.get(0));

        ResultActions response = mockMvc.perform(
                get("/product/{id}", newProduct.getId())
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()",
                        CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Test Product GetById Success - GET Endpoint")
    void getById_returnsProductDetailedResponse_whenSuccess() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), sellerModel.getId(), representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);

        ProductModel newProduct = productRepository.save(productBatchStockRequest.toProduct());
        List<BatchStockModel> productBatchsStockList = productBatchStockRequest.toBatchStock();
        productBatchsStockList.get(0).setProduct(newProduct);


        batchStockRepository.save(productBatchsStockList.get(0));

        ResultActions response = mockMvc.perform(
                get("/product/{id}", newProduct.getId())
                        .content(objectMapper.writeValueAsString(productBatchStockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", CoreMatchers.is(newProduct.getId().intValue())))
                .andExpect(jsonPath("$.batchStock[0].sectionId", CoreMatchers.is(inboundOrderRequest.getBatchStock().get(0).getSectionCode().intValue())))
                .andExpect(jsonPath("$.batchStock[0].productQuantity", CoreMatchers.is(inboundOrderRequest.getBatchStock().get(0).getProductQuantity())));
    }

    @Test
    @DisplayName("Test Product GetByCategory Success - GET Endpoint")
    void getByCategory_returnsListOfProductDetailedResponse_whenSuccess() throws Exception {
        WarehouseModel warehouseModel = createWarehouse();
        SellerModel sellerModel = createSeller();
        SectionModel sectionModel = createSection(warehouseModel.getId());
        RepresentativeModel representativeModel = createRepresentative(warehouseModel.getId());

        BatchStockRequest batchStockRequest = createBatchStockRequest(sectionModel.getId());

        List<BatchStockRequest> batchStockRequestList = new ArrayList<>();
        batchStockRequestList.add(batchStockRequest);
        InboundOrderRequest inboundOrderRequest = createInboundOrderRequest(warehouseModel.getId(), sellerModel.getId(), representativeModel.getId(), batchStockRequestList);

        ProductBatchStockRequest productBatchStockRequest = new ProductBatchStockRequest();
        productBatchStockRequest.setInboundOrder(inboundOrderRequest);

        ProductModel newProduct = productRepository.save(productBatchStockRequest.toProduct());
        List<BatchStockModel> productBatchsStockList = productBatchStockRequest.toBatchStock();
        productBatchsStockList.get(0).setProduct(newProduct);


        batchStockRepository.save(productBatchsStockList.get(0));

        ResultActions response = mockMvc.perform(
                get("/product/list?querytype=FS")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",
                        CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Test Product GetByCategory with Empty value - GET Endpoint")
    void getByCategory_returnsNotFound_whenCategoryIsEmpty() throws Exception {

        ResultActions response = mockMvc.perform(
                get("/product/list?querytype=FS")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isNotFound());
    }

    WarehouseModel createWarehouse() {
        String city = "Tramandaí";
        String district = "Zona Nova";
        String state = "Rio Grande do Sul";
        String postalCode = "99999999";
        String street = "Avenida Emancipacao";

        WarehouseRequest newWarehouseRequest = WarehouseRequest
                .builder()
                .city(city)
                .street(street)
                .state(state)
                .postalCode(postalCode)
                .district(district).build();
        return warehouseRepository.save(newWarehouseRequest.toModel());
    }

    SellerModel createSeller() {
        Long id = 1L;
        String name = "Vendedor 1";
        String cpf = "12345678900";
        double rating = 4.2;

        SellerModel seller = new SellerModel(id, name, cpf, rating);

        return sellerRepository.save(seller);
    }

    RepresentativeModel createRepresentative(long warehouseCode) {
        String name = "Representative name";
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouseCode);

        RepresentativeModel responseModel = new RepresentativeModel();
        responseModel.setName(name);
        responseModel.setWarehouse(warehouseResponse);

        return representativeRepository.save(responseModel);
    }

    SectionModel createSection(long warehouseCode) {
        String description = "Laranja Bahia";
        CategoryEnum category = CategoryEnum.FRESH;
        Double totalSize = 5.5;
        Double temperature = 2.0;

        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouseCode);

        SectionModel sectionModel = new SectionModel();
        sectionModel.setCategory(category);
        sectionModel.setDescription(description);
        sectionModel.setTotalSize(totalSize);
        sectionModel.setTemperature(temperature);
        sectionModel.setWarehouse(warehouseResponse);

        return sectionRepository.save(sectionModel);
    }

    BatchStockRequest createBatchStockRequest(Long sectionCode) {
        Long batchstockSectionCode = sectionCode;
        String batchstockBatchNumber = "TST-123";
        Integer batchstockProductQuantity = 1;
        LocalDate batchstockManufacturingDate = LocalDate.now().minusWeeks(1);
        LocalDateTime batchstockManufacturingDatetime = LocalDateTime.now();
        LocalDate batchstockDueDate = LocalDate.now().plusWeeks(10);
        BatchStockRequest batchStockRequest = BatchStockRequest.builder()
                .productQuantity(batchstockProductQuantity)
                .batchNumber(batchstockBatchNumber)
                .manufacturingDatetime(batchstockManufacturingDatetime)
                .manufacturingDate(batchstockManufacturingDate)
                .dueDate(batchstockDueDate)
                .sectionCode(batchstockSectionCode)
                .build();

        return batchStockRequest;
    }

    InboundOrderRequest createInboundOrderRequest(long warehouseCode, long sellerCode, long representativeCode, List<BatchStockRequest> batchStockRequestList) {
        String productTitle = "Test";
        String productDescription = "Test";
        Double unitVolume = Double.valueOf(1.0);
        Double unitWeight = Double.valueOf(1.0);
        BigDecimal price = BigDecimal.valueOf(1.0);
        CategoryEnum category = CategoryEnum.FRESH;

        InboundOrderRequest inboundOrderRequest = InboundOrderRequest.builder()
                .batchStock(batchStockRequestList)
                .price(price)
                .productDescription(productDescription)
                .productTitle(productTitle)
                .representativeCode(representativeCode)
                .sellerCode(sellerCode)
                .unitVolume(unitVolume)
                .unitWeight(unitWeight)
                .warehouseCode(warehouseCode)
                .category(category)
                .build();

        return inboundOrderRequest;
    }
}