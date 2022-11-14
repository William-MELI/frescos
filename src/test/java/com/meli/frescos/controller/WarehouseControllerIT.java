package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import com.meli.frescos.service.IWarehouseService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WarehouseControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IWarehouseRepository warehouseRepository;

    @Autowired
    private IWarehouseService warehouseService;


    @Test
    @DisplayName("Test Warehouse Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
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

        ResultActions response = mockMvc.perform(
                post("/warehouse")
                        .content(objectMapper.writeValueAsString(newWarehouseRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test Warehouse creation - POST Endpoint")
    void create_throwException_whenPassWrongParameters() throws Exception {
        String city = "";
        String district = "";
        String state = "";
        String postalCode = "";

        WarehouseRequest newWarehouseRequest = WarehouseRequest
                .builder()
                .city(city)
                .street(null)
                .state(state)
                .postalCode(postalCode)
                .district(district).build();

        ResultActions response = mockMvc.perform(
                post("/warehouse")
                        .content(objectMapper.writeValueAsString(newWarehouseRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Get Warehouse by ID - GET Endpoint")
    void getById_returnsWarehouse_whenIdIsAvailable() throws Exception {
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

        WarehouseModel warehouse = warehouseService.save(newWarehouseRequest.toModel());

        ResultActions response = mockMvc.perform(
                get("/warehouse/{id}", String.valueOf(warehouse.getId()))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Warehouse ID not found - GET Endpoint")
    void getById_throwsWarehouseNotFound_whenIdDoesNotExists() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/warehouse/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isNotFound());
    }

    @Test
    void getAll_returnListOfWarehouse_whenSuccess() throws Exception {
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

        warehouseService.save(newWarehouseRequest.toModel());

        ResultActions response = mockMvc.perform(
                get("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }

    @Test
    void delete_returnOkStatus_whenSuccess() throws Exception {
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

        WarehouseModel warehouse = warehouseService.save(newWarehouseRequest.toModel());

        ResultActions response = mockMvc.perform(
                delete("/warehouse/{id}", (warehouse.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());
    }


}