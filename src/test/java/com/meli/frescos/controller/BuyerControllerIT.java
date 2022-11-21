package com.meli.frescos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.BuyerRequest;
import com.meli.frescos.model.BuyerModel;
import com.meli.frescos.repository.BuyerRepository;
import com.meli.frescos.service.IBuyerService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BuyerControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private IBuyerService buyerService;

    @BeforeEach
    void setup() {
        this.buyerRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("Test Buyer Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
        String name = "Buyer";
        String cpf = "41937616576";

        BuyerRequest buyerRequest = BuyerRequest.builder()
                .cpf(cpf)
                .name(name)
                .build();

        ResultActions response = mockMvc.perform(
                post("/buyer")
                        .content(objectMapper.writeValueAsString(buyerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test invalid Buyer creation - POST Endpoint")
    void create_throwException_whenWrongParameters() throws Exception {
        String name = "";
        String cpf = "";

        BuyerRequest buyerRequest = BuyerRequest.builder()
                .cpf(cpf)
                .name(name)
                .build();

        ResultActions response = mockMvc.perform(
                post("/buyer")
                        .content(objectMapper.writeValueAsString(buyerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Get Warehouse by ID - GET Endpoint")
    void getById_returnsWarehouse_whenIdIsAvailable() throws Exception {
        String cpf = "12345678900";
        String name = "Buyer";

        BuyerRequest buyerRequest = BuyerRequest.builder()
                .cpf(cpf)
                .name(name)
                .build();

        BuyerModel buyer = buyerService.save(buyerRequest.toModel());

        ResultActions response = mockMvc.perform(
                get("/buyer/{id}", String.valueOf(buyer.getId()))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Warehouse ID not found - GET Endpoint")
    void getById_throwsWarehouseNotFound_whenIdDoesNotExists() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/buyer/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Get all Warehouse - GET Endpoint")
    void getAll_returnListOfWarehouse_whenSuccess() throws Exception {
        String name = "Buyer";
        String cpf = "12345678900";

        BuyerRequest buyerRequest = BuyerRequest.builder()
                .cpf(cpf)
                .name(name)
                .build();

        buyerService.save(buyerRequest.toModel());

        ResultActions response = mockMvc.perform(
                get("/buyer")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Update Buyer info - PUT Endpoint")
    void update_returnsUpdatedBuyer_whenSuccess() throws Exception {
        String cpf = "12345678900";
        String name = "Buyer";
        Long id;

        BuyerRequest buyerRequest = BuyerRequest.builder()
                .cpf(cpf)
                .name(name)
                .build();

        id = buyerService.save(buyerRequest.toModel()).getId();

        String newCpf = "95925955005";
        String newName = "NewBuyer";

        BuyerModel newBuyer =
                BuyerRequest.builder()
                        .cpf(newCpf)
                        .name(newName)
                        .build().toModel();

        ResultActions response = mockMvc.perform(
                put("/buyer/{id}", id)
                        .content(objectMapper.writeValueAsString(newBuyer))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String expectedJson = String.format("{'name': '%s'}", newBuyer.getName());

        response.andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }
}