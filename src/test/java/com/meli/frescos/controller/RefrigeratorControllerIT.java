package com.meli.frescos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.frescos.controller.dto.RefrigeratorRequest;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.Refrigerator;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.RefrigeratorRepository;
import com.meli.frescos.repository.SectionRepository;
import com.meli.frescos.repository.WarehouseRepository;
import com.meli.frescos.service.IRefrigeratorService;
import com.meli.frescos.service.ISectionService;
import com.meli.frescos.service.IWarehouseService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RefrigeratorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private static WarehouseModel warehouseModel = WarehouseModel.builder()
            .district("District Teste")
            .state("State Teste")
            .city("City Teste")
            .street("Street Teste")
            .postalCode("45616516")
            .build();

    @Test
    @DisplayName("Test Refrigerator successful creation - POST Endpoint")
    void save_returnsCreatedStatus_whenSuccess() throws Exception {
        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        List<Refrigerator> refrigeratorListBefore = refrigeratorRepository.findAll();

        RefrigeratorRequest refrigeratorRequest = RefrigeratorRequest.builder()
                .brand("Marca POST")
                .model("Modelo POST")
                .build();

        ResultActions response = mockMvc.perform(
                post("/refrigerator")
                        .content(objectMapper.writeValueAsString(refrigeratorRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        List<Refrigerator> refrigeratorListAfter = refrigeratorRepository.findAll();
        assertEquals(refrigeratorListBefore.size(), refrigeratorListAfter.size() - 1);
        refrigeratorListAfter.removeAll(refrigeratorListBefore);
        Refrigerator savedRefrigerator = refrigeratorListAfter.get(0);

        assertEquals(savedRefrigerator.getBrand(), refrigeratorRequest.getBrand());
        assertEquals(savedRefrigerator.getModel(), refrigeratorRequest.getModel());
        assertEquals(savedRefrigerator.getTemperature(), 25D);
        assertEquals(savedRefrigerator.getTurnedOn(), false);
        assertNull(savedRefrigerator.getLastRevision());
        assertNull(savedRefrigerator.getSection());

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand", CoreMatchers.is(savedRefrigerator.getBrand())))
                .andExpect(jsonPath("$.model", CoreMatchers.is(savedRefrigerator.getModel())))
                .andExpect(jsonPath("$.temperature", CoreMatchers.is(savedRefrigerator.getTemperature())))
                .andExpect(jsonPath("$.turnedOn", CoreMatchers.is(savedRefrigerator.getTurnedOn())))
                .andExpect(jsonPath("$.lastRevision").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.section").value(IsNull.nullValue()));

        savedRefrigerator.setAcquired(acquired);
        savedRefrigerator.setLastRevision(lastRev);
        refrigeratorRepository.save(savedRefrigerator);

    }

    @Test
    @DisplayName("Test Refrigerator successful getAll - GET Endpoint")
    void getAll_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .build();

        refrigeratorRepository.save(refrigerator);
        List<Refrigerator> refrigeratorList = refrigeratorRepository.findAll();
        List<Double> temperatures = new ArrayList<>();
        for (Refrigerator refr : refrigeratorList) {
            temperatures.add(refr.getTemperature());
            SectionModel sect = refr.getSection();
            if (sect != null) temperatures.add(sect.getTemperature());
        }

        ResultActions response = mockMvc.perform(get("/refrigerator"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$..brand", CoreMatchers.is(refrigeratorList.stream().map(Refrigerator::getBrand).toList())))
                .andExpect(jsonPath("$..model", CoreMatchers.is(refrigeratorList.stream().map(Refrigerator::getModel).toList())))
                .andExpect(jsonPath("$..temperature", CoreMatchers.is(temperatures)))
                .andExpect(jsonPath("$..turnedOn", CoreMatchers.is(refrigeratorList.stream().map(Refrigerator::getTurnedOn).toList())))
                .andExpect(jsonPath("$..lastRevision", CoreMatchers.is(refrigeratorList.stream().map(r -> r.getLastRevision().truncatedTo(ChronoUnit.SECONDS).toString()).toList())));
    }

    @Test
    @DisplayName("Test Refrigerator successful getById - GET Endpoint")
    void getById_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        warehouseRepository.save(warehouseModel);
        SectionModel sectionModel = sectionRepository.save(SectionModel.builder()
                .description("Description Teste")
                .category(CategoryEnum.REFRIGERATED)
                .totalSize(400D)
                .temperature(25D)
                .warehouse(warehouseModel)
                .build());

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .section(sectionModel)
                .build();

        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);

        ResultActions response = mockMvc.perform(get("/refrigerator/" + savedRefrigerator.getId()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(savedRefrigerator.getId().intValue())))
                .andExpect(jsonPath("$.brand", CoreMatchers.is(savedRefrigerator.getBrand())))
                .andExpect(jsonPath("$.model", CoreMatchers.is(savedRefrigerator.getModel())))
                .andExpect(jsonPath("$.temperature", CoreMatchers.is(savedRefrigerator.getTemperature())))
                .andExpect(jsonPath("$.turnedOn", CoreMatchers.is(savedRefrigerator.getTurnedOn())))
                .andExpect(jsonPath("$.lastRevision", CoreMatchers.is(lastRev.truncatedTo(ChronoUnit.SECONDS).toString())))
                .andExpect(jsonPath("$.section.id", CoreMatchers.is(sectionModel.getId().intValue())));
    }

    @Test
    @DisplayName("Test Refrigerator successful getBySectionId - GET Endpoint")
    void getBySectionId_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        warehouseRepository.save(warehouseModel);
        SectionModel sectionModel = sectionRepository.save(SectionModel.builder()
                .description("Description Teste")
                .category(CategoryEnum.REFRIGERATED)
                .totalSize(400D)
                .temperature(25D)
                .warehouse(warehouseModel)
                .build());

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .section(sectionModel)
                .build();

        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);

        ResultActions response = mockMvc.perform(get("/refrigerator/section/" + savedRefrigerator.getSection().getId().toString()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", CoreMatchers.is(List.of(savedRefrigerator.getId().intValue(), sectionModel.getId().intValue(), warehouseModel.getId().intValue()))))
                .andExpect(jsonPath("$..brand", CoreMatchers.is(List.of(savedRefrigerator.getBrand()))))
                .andExpect(jsonPath("$..model", CoreMatchers.is(List.of(savedRefrigerator.getModel()))))
                .andExpect(jsonPath("$..temperature", CoreMatchers.is(List.of(savedRefrigerator.getTemperature(), sectionModel.getTemperature()))))
                .andExpect(jsonPath("$..turnedOn", CoreMatchers.is(List.of(savedRefrigerator.getTurnedOn()))))
                .andExpect(jsonPath("$..lastRevision", CoreMatchers.is(List.of(lastRev.truncatedTo(ChronoUnit.SECONDS).toString()))));
    }

    @Test
    @DisplayName("Test Refrigerator successful setSection - PATCH Endpoint")
    void setSection_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        warehouseRepository.save(warehouseModel);
        SectionModel sectionModel = sectionRepository.save(SectionModel.builder()
                .description("Description Teste")
                .category(CategoryEnum.REFRIGERATED)
                .totalSize(400D)
                .temperature(25D)
                .warehouse(warehouseModel)
                .build());

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .build();

        refrigerator = refrigeratorRepository.save(refrigerator);

        ResultActions response = mockMvc.perform(
                patch("/refrigerator/section/")
                        .param("refrigeratorId", refrigerator.getId().toString())
                        .param("sectionId", sectionModel.getId().toString())
        );

        Refrigerator savedRefrigerator = refrigeratorRepository.findById(refrigerator.getId()).get();

        assertEquals(savedRefrigerator.getSection().getId(), sectionModel.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$..id", CoreMatchers.is(List.of(savedRefrigerator.getId().intValue(), sectionModel.getId().intValue(), warehouseModel.getId().intValue()))))
                .andExpect(jsonPath("$..brand", CoreMatchers.is(List.of(savedRefrigerator.getBrand()))))
                .andExpect(jsonPath("$..model", CoreMatchers.is(List.of(savedRefrigerator.getModel()))))
                .andExpect(jsonPath("$..temperature", CoreMatchers.is(List.of(savedRefrigerator.getTemperature(), sectionModel.getTemperature()))))
                .andExpect(jsonPath("$..turnedOn", CoreMatchers.is(List.of(savedRefrigerator.getTurnedOn()))))
                .andExpect(jsonPath("$..lastRevision", CoreMatchers.is(List.of(lastRev.truncatedTo(ChronoUnit.SECONDS).toString()))));
    }

    @Test
    @DisplayName("Test Refrigerator successful setTurnedOn - PATCH Endpoint")
    void setTurnedOn_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        warehouseRepository.save(warehouseModel);
        SectionModel sectionModel = sectionRepository.save(SectionModel.builder()
                .description("Description Teste")
                .category(CategoryEnum.REFRIGERATED)
                .totalSize(400D)
                .temperature(25D)
                .warehouse(warehouseModel)
                .build());

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .build();

        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);

        ResultActions response = mockMvc.perform(
                patch("/refrigerator/turnedon/")
                        .param("refrigeratorId", savedRefrigerator.getId().toString())
                        .param("turnedOn", "false")
        );

        savedRefrigerator = refrigeratorRepository.findById(savedRefrigerator.getId()).get();

        assertEquals(savedRefrigerator.getTurnedOn(), false);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(savedRefrigerator.getId().intValue())))
                .andExpect(jsonPath("$.brand", CoreMatchers.is(savedRefrigerator.getBrand())))
                .andExpect(jsonPath("$.model", CoreMatchers.is(savedRefrigerator.getModel())))
                .andExpect(jsonPath("$.temperature", CoreMatchers.is(savedRefrigerator.getTemperature())))
                .andExpect(jsonPath("$.turnedOn", CoreMatchers.is(savedRefrigerator.getTurnedOn())))
                .andExpect(jsonPath("$.lastRevision", CoreMatchers.is(lastRev.truncatedTo(ChronoUnit.SECONDS).toString())));
    }

    @Test
    @DisplayName("Test Refrigerator successful setTurnedOn - PATCH Endpoint")
    void setTemperature_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        warehouseRepository.save(warehouseModel);
        SectionModel sectionModel = sectionRepository.save(SectionModel.builder()
                .description("Description Teste")
                .category(CategoryEnum.REFRIGERATED)
                .totalSize(400D)
                .temperature(25D)
                .warehouse(warehouseModel)
                .build());

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .build();

        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);

        ResultActions response = mockMvc.perform(
                patch("/refrigerator/temperature/")
                        .param("refrigeratorId", savedRefrigerator.getId().toString())
                        .param("temperature", "-5.0")
        );

        savedRefrigerator = refrigeratorRepository.findById(savedRefrigerator.getId()).get();

        assertEquals(savedRefrigerator.getTemperature(), -5D);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(savedRefrigerator.getId().intValue())))
                .andExpect(jsonPath("$.brand", CoreMatchers.is(savedRefrigerator.getBrand())))
                .andExpect(jsonPath("$.model", CoreMatchers.is(savedRefrigerator.getModel())))
                .andExpect(jsonPath("$.temperature", CoreMatchers.is(savedRefrigerator.getTemperature())))
                .andExpect(jsonPath("$.turnedOn", CoreMatchers.is(savedRefrigerator.getTurnedOn())))
                .andExpect(jsonPath("$.lastRevision", CoreMatchers.is(lastRev.truncatedTo(ChronoUnit.SECONDS).toString())));
    }

    @Test
    @DisplayName("Test Refrigerator successful setTurnedOn - PATCH Endpoint")
    void executeRevision_returnsOkStatus_whenSuccess() throws Exception {

        LocalDateTime acquired = LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime lastRev = acquired.plusDays(1).truncatedTo(ChronoUnit.SECONDS);

        warehouseRepository.save(warehouseModel);
        SectionModel sectionModel = sectionRepository.save(SectionModel.builder()
                .description("Description Teste")
                .category(CategoryEnum.REFRIGERATED)
                .totalSize(400D)
                .temperature(25D)
                .warehouse(warehouseModel)
                .build());

        Refrigerator refrigerator = Refrigerator.builder()
                .brand("Brand1")
                .model("Model1")
                .temperature(25D)
                .turnedOn(true)
                .acquired(acquired)
                .lastRevision(lastRev)
                .build();

        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);

        ResultActions response = mockMvc.perform(
                patch("/refrigerator/revision/")
                        .param("refrigeratorId", savedRefrigerator.getId().toString())
        );

        savedRefrigerator = refrigeratorRepository.findById(savedRefrigerator.getId()).get();

        assertNotEquals(savedRefrigerator.getLastRevision(), lastRev);

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(savedRefrigerator.getId().intValue())))
                .andExpect(jsonPath("$.brand", CoreMatchers.is(savedRefrigerator.getBrand())))
                .andExpect(jsonPath("$.model", CoreMatchers.is(savedRefrigerator.getModel())))
                .andExpect(jsonPath("$.temperature", CoreMatchers.is(savedRefrigerator.getTemperature())))
                .andExpect(jsonPath("$.turnedOn", CoreMatchers.is(savedRefrigerator.getTurnedOn())))
                .andExpect(jsonPath("$.lastRevision", CoreMatchers.is(savedRefrigerator.getLastRevision().truncatedTo(ChronoUnit.SECONDS).toString())));
    }

}
