package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import com.meli.frescos.repository.SectionRepository;
import com.meli.frescos.service.ISectionService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SectionControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IWarehouseRepository warehouseRepo;

    @Autowired
    private SectionRepository sectionRepo;

    @BeforeEach
    void setup() {
        this.sectionRepo.deleteAll();
        this.warehouseRepo.deleteAll();
    }

    private void newWarehouseRecord() {
        WarehouseModel newWarehouse = new WarehouseModel("Tramandaí", "Rio Grande do Sul", "Avenida Emancipacao", "99999999", "Zona Sul");
        warehouseService.save(newWarehouse);
    }


    @Test
    @DisplayName("Test Section Successfull Creation - POST Endpoint")
    void create_returnsCreatedStatus_whenSuccess()  throws Exception {
        newWarehouseRecord();
        String description = "marca";
        CategoryEnum category = CategoryEnum.FRESH;
        Double totalSize = 40.0;
        Double temperature = 4.8;
        Long warehouse = 1L;

        SectionRequest sectionRequest = SectionRequest.builder()
                .description(description)
                .category(category)
                .totalSize(totalSize)
                .temperature(temperature)
                .warehouse(warehouse)
                .build();

        ResultActions response = mockMvc.perform(
                post("/section")
                        .content(objectMapper.writeValueAsString(sectionRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Test Section creating - POST Endpoint")
    void create_throwException_whenWarehouseNotFound() throws Exception {
        String description = "marca";
        CategoryEnum category = CategoryEnum.FRESH;
        Double totalSize = 40.0;
        Double temperature = 4.8;
        Long warehouse = 1L;

        SectionRequest sectionRequest = SectionRequest.builder()
                .description(description)
                .category(category)
                .totalSize(totalSize)
                .temperature(temperature)
                .warehouse(warehouse)
                .build();

        ResultActions response =  mockMvc.perform(
                post("/section")
                        .content(objectMapper.writeValueAsBytes(sectionRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isNotFound());
    }
}
