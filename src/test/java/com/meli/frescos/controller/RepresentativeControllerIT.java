package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.RepresentativeRequest;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.RepresentativeRepository;
import com.meli.frescos.service.IRepresentativeService;
import com.meli.frescos.service.IWarehouseService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RepresentativeControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IRepresentativeService representativeService;

    @Autowired
    private RepresentativeRepository representativeRepository;

    private WarehouseModel newWarehouseRecord() {
        WarehouseModel newWarehouse = new WarehouseModel("Tramandaí", "Rio Grande do Sul", "Avenida Emancipacao", "99999999", "Zona Sul");
        return warehouseService.save(newWarehouse);
    }

    @BeforeEach
    void setup() {
        this.representativeRepository.deleteAll();
    }

    @Test
    @DisplayName("Test Representative Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess() throws Exception {
        WarehouseModel warehouse = newWarehouseRecord();
        String name = "Representante 1";

        RepresentativeRequest representativeRequest = RepresentativeRequest.builder()
                .name(name)
                .warehouseCode(warehouse.getId())
                .build();

        ResultActions response = mockMvc.perform(
                post("/representative")
                        .content(objectMapper.writeValueAsString(representativeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isCreated());
    }

    @Test
    void create_returnsThrowWarehouseNotFoundException_whenInexistenceWareehouse() throws Exception {
        String name = "Representante 1";

        RepresentativeRequest representativeRequest = RepresentativeRequest.builder()
                .name(name)
                .warehouseCode(10000L)
                .build();

        ResultActions response = mockMvc.perform(
                post("/representative")
                        .content(objectMapper.writeValueAsString(representativeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isNotFound())
                .andExpect(
                        result -> assertTrue(
                                result.getResolvedException() instanceof WarehouseNotFoundException)
                );
    }

    @Test
    void getById_returnsRepresentative_whenIdIsAvailable() throws Exception {
        WarehouseModel warehouse = newWarehouseRecord();
        String name = "Representante 1";

        RepresentativeModel representativeModel = RepresentativeModel.builder()
                .name(name)
                .warehouse(warehouse)
                .build();

       RepresentativeModel representative = representativeService.save(representativeModel, representativeModel.getWarehouse().getId());

       ResultActions response =  mockMvc.perform(
                get("/representative/{id}", String.valueOf(representative.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isOk());
    }

    @Test
    void getAll_returnListOfRepresentative_whenSuccess() throws Exception {
        WarehouseModel warehouseUm = newWarehouseRecord();
        WarehouseModel warehouseDois = newWarehouseRecord();
        String nameUm = "Representante 1";
        String nameDois = "Representante 2";

        RepresentativeModel representativeModel = RepresentativeModel.builder()
                .name(nameUm)
                .warehouse(warehouseUm)
                .build();

        RepresentativeModel representativeModelDois = RepresentativeModel.builder()
                .name(nameDois)
                .warehouse(warehouseDois)
                .build();

        representativeService.save(representativeModel, representativeModel.getWarehouse().getId());
        representativeService.save(representativeModelDois, representativeModelDois.getWarehouse().getId());

        ResultActions response = mockMvc.perform(
                get("/representative")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk());

    }
}
