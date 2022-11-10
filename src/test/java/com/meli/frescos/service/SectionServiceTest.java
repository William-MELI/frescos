package com.meli.frescos.service;

import com.meli.frescos.controller.dto.SectionRequest;
import com.meli.frescos.model.CategoryEnum;
import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import com.meli.frescos.repository.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    @InjectMocks
    SectionService sectionService;

    @Mock
    SectionRepository sectionRepository;

    @Mock
    IWarehouseRepository warehouseRepository;

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Create a new Section successfully")
    void createNewSection_returnCreatedSection_whenSuccess() {
        String description = "Laranja Bahia";
        CategoryEnum category = CategoryEnum.FRESH;
        Double totalSize = 5.5;
        Double temperature = 2.0;
        Long warehouse_id = 1L;
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        SectionRequest newSectionRequest = SectionRequest
                .builder()
                .description(description)
                .category(category)
                .totalSize(totalSize)
                .temperature(temperature)
                .warehouse(warehouse_id).build();

        SectionModel responseModel = SectionModel
                .builder()
                .description(description)
                .category(category)
                .totalSize(totalSize)
                .temperature(temperature)
                .warehouse(warehouseResponse)
                .build();

        Set<ConstraintViolation<SectionRequest>> violations = validator.validate(newSectionRequest);
        Mockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(new WarehouseModel()));
        Mockito.when(sectionRepository.save(ArgumentMatchers.any())).thenReturn(responseModel);

        SectionModel responseSection = sectionService.save(newSectionRequest);

        assertEquals(description, responseSection.getDescription());
        assertEquals(category, responseSection.getCategory());
        assertEquals(totalSize, responseSection.getTotalSize());
        assertEquals(temperature, responseSection.getTemperature());
        assertEquals(warehouse_id, responseSection.getWarehouse().getId());
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Throw exception when ID Warehouse is not found.")
    void insert_throwsException_WhenIdWarehouseIsInvalid() {
        String description = "Laranja Bahia";
        CategoryEnum category = CategoryEnum.FRESH;
        Double totalSize = 5.5;
        Double temperature = 2.0;

        SectionRequest newSectionRequest = SectionRequest
                .builder()
                .description(description)
                .category(category)
                .totalSize(totalSize)
                .temperature(temperature)
                .build();

        assertThrows(NullPointerException.class, () -> {
            SectionModel responsSection = sectionService.save(newSectionRequest);
        });
    }

    @Test
    @DisplayName("Returns a Section by ID ")
    void getById_returnsSection_WhenSuccess() throws Exception {
        String description = "Laranja Bahia";
        CategoryEnum category = CategoryEnum.FRESH;
        Double totalSize = 5.5;
        Double temperature = 2.0;
        Long warehouse_id = 1L;
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        SectionModel responseModel = SectionModel
                .builder()
                .description(description)
                .category(category)
                .totalSize(totalSize)
                .temperature(temperature)
                .warehouse(warehouseResponse)
                .build();

        Mockito.when(sectionRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(responseModel));

        SectionModel responseSection = sectionService.findById(ArgumentMatchers.anyLong());

        assertEquals(description, responseSection.getDescription());
        assertEquals(category, responseSection.getCategory());
        assertEquals(totalSize, responseSection.getTotalSize());
        assertEquals(temperature, responseSection.getTemperature());
        assertEquals(warehouse_id, responseSection.getWarehouse().getId());
    }

    @Test
    @DisplayName("Returns a All Section")
    void getAll_returnsAllSection_WhenSuccess() {
        List<SectionModel> sectionList = new ArrayList<>();

        WarehouseModel warehouse =  new WarehouseModel("blumenau", "Santa Catarina", "15 de Maio", "5662sdww", "aaaaa");

        sectionList.add(new SectionModel("Banana",CategoryEnum.FRESH, 6.8, 5.5, warehouse ));
        sectionList.add(new SectionModel("peixe", CategoryEnum.FROZEN, 10.5, 0.0, warehouse));

        Mockito.when(sectionRepository.findAll())
                .thenReturn(sectionList);

        List<SectionModel> responseSection = sectionService.findAll();

        assertEquals(sectionList, responseSection);
        assertEquals(2, responseSection.size());
    }
}
