package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.SellerRequest;
import com.meli.frescos.exception.CpfDuplicateException;
import com.meli.frescos.exception.SellerByIdNotFoundException;
import com.meli.frescos.model.SellerModel;
import com.meli.frescos.repository.SellerRepository;
import com.meli.frescos.service.ISellerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SellerControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ISellerService sellerService;

    @BeforeEach
    void setup() {
        this.sellerRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("Test Seller Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Test Seller Creation with CPF Duplicate  - POST Endpoint")
    void create_throws_whenCPFIsDuplicated() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        sellerService.save(sellerRequest.toModel());

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof CpfDuplicateException))
                .andExpect(jsonPath("$.message", CoreMatchers.is(String.format("CPF %s já registrado.", cpf))));
    }

    @Test
    @DisplayName("Test Seller Creation with Empty CPF - POST Endpoint")
    void create_throwsMethodArgumentNotValidException_whenCPFIsEmpty() throws Exception {
        String name = "Seller";
        String cpf = "";
        Double rating = Double.valueOf(5.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString("O CPF do vendedor deve ser preenchido.")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString("CPF inválido")));
    }

    @Test
    @DisplayName("Test Seller Creation with Invalid CPF - POST Endpoint")
    void create_throwsMethodArgumentNotValidException_whenCPFIsInvalid() throws Exception {
        String name = "Seller";
        String cpf = "-1";
        Double rating = Double.valueOf(5.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.is("CPF inválido")));
    }

    @Test
    @DisplayName("Test Seller Creation with Empty Name - POST Endpoint")
    void create_throws_whenNameIsEmpty() throws Exception {
        String name = "";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.is("O nome do vendedor deve ser preenchido.")));
    }

    @Test
    @DisplayName("Test Seller Creation with wrong rating - POST Endpoint")
    void create_throwsMethodArgumentNotValidException_whenRatingIsAboveMax() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(10.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString("A avaliação do vendedor não pode ser maior que 5 e deve conter no máximo duas casas decimais")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString("A avaliação do vendedor deve ser de 0 à 5")));
    }

    @Test
    @DisplayName("Test Seller Creation with wrong rating  - POST Endpoint")
    void create_throwsMethodArgumentNotValidException_whenRatingIsBelowMin() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(-10.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();

        ResultActions response = mockMvc.perform(
                post("/seller")
                        .content(objectMapper.writeValueAsString(sellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString("A avaliação do vendedor não pode ser maior que 5 e deve conter no máximo duas casas decimais")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString("A avaliação do vendedor deve ser de 0 à 5")));
    }

    @Test
    @DisplayName("Test Get all Seller - GET Endpoint")
    void getAll_returnListOfSeller_whenSuccess() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();


        sellerService.save(sellerRequest.toModel());

        ResultActions response = mockMvc.perform(
                get("/seller")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Test Get Seller by ID - GET Endpoint")
    void getByID_returnSeller_whenSuccess() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);
        Long id;

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();


        SellerModel newSeller = sellerService.save(sellerRequest.toModel());
        id = newSeller.getId();

        ResultActions response = mockMvc.perform(
                get("/seller/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(name)))
                .andExpect(jsonPath("$.rating", CoreMatchers.is(rating.doubleValue())))
                .andExpect(jsonPath("$.id", CoreMatchers.is(id.intValue())));
    }

    @Test
    @DisplayName("Test Get Seller by ID throws Exception - GET Endpoint")
    void getById_throwsSellerByIdNotFoundException_whenSuccess() throws SellerByIdNotFoundException, Exception {
        ResultActions response = mockMvc.perform(
                get("/seller/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof SellerByIdNotFoundException))
                .andExpect(jsonPath("$.message", CoreMatchers.containsString("Vendedor com id -1 não encontrado")));
    }

    @Test
    @DisplayName("Test Update Seller info - PUT Endpoint")
    void update_returnsUpdatedSeller_whenSuccess() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);
        Long id;

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();


        SellerModel newSeller = sellerService.save(sellerRequest.toModel());
        id = newSeller.getId();

        String newCpf = "95925955005";
        String newName = "NewSeller";

        SellerModel newSellerRequest =
                SellerRequest.builder()
                        .cpf(newCpf)
                        .name(newName)
                        .build().toModel();

        ResultActions response = mockMvc.perform(
                put("/seller/{id}", id)
                        .content(objectMapper.writeValueAsString(newSellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String expectedJsonName = String.format("{'name': '%s'}", newSellerRequest.getName());

        response.andExpect(status().isOk())
                .andExpect(content().json(expectedJsonName));

    }

    @Test
    @DisplayName("Test Update Seller Exception - PUT Endpoint")
    void update_throws_whenNoData() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);
        Long id;

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();


        SellerModel newSeller = sellerService.save(sellerRequest.toModel());
        id = newSeller.getId();

        SellerModel newSellerRequest =
                SellerRequest.builder()
                        .cpf(null)
                        .name(null)
                        .build().toModel();

        ResultActions response = mockMvc.perform(
                put("/seller/{id}", id)
                        .content(objectMapper.writeValueAsString(newSellerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );


        response.andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Test Delete Seller by ID - GET Endpoint")
    void deleteByIdetByID_returnNoContent_whenSuccess() throws Exception {
        String name = "Seller";
        String cpf = "41937616576";
        Double rating = Double.valueOf(5.0);
        Long id;

        SellerRequest sellerRequest = SellerRequest.builder()
                .cpf(cpf)
                .name(name)
                .rating(rating)
                .build();


        SellerModel newSeller = sellerService.save(sellerRequest.toModel());
        id = newSeller.getId();

        ResultActions response = mockMvc.perform(
                delete("/seller/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Delete Seller by ID with No Seller- GET Endpoint")
    void deleteByIdetByID_returnNoContent_whenNoSellerExists() throws Exception {

        Long id = 1L;

        ResultActions response = mockMvc.perform(
                delete("/seller/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof SellerByIdNotFoundException))
                .andExpect(jsonPath("$.message", CoreMatchers.containsString("Vendedor com id 1 não encontrado")));
    }
}