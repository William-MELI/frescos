package com.meli.frescos.service;

import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
class WarehouseServiceTest {

    @InjectMocks
    WarehouseService warehouseService;

    @Mock
    IWarehouseRepository warehouseRepository;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Create a new Warehouse successfully")
    void createNewWarehouse_returnsCreatedWarehouse_whenSuccess() {
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

        WarehouseModel newWarehouseEntity = newWarehouseRequest.toModel();

        Set<ConstraintViolation<WarehouseRequest>> violations = validator.validate(newWarehouseRequest);
        Mockito.when(warehouseRepository.save(ArgumentMatchers.any())).thenReturn(newWarehouseEntity);

        WarehouseModel responseWarehouse = warehouseService.save(newWarehouseEntity);

        assertEquals(city, responseWarehouse.getCity());
        assertEquals(district, responseWarehouse.getDistrict());
        assertEquals(state, responseWarehouse.getState());
        assertEquals(postalCode, responseWarehouse.getPostalCode());
        assertEquals(street, responseWarehouse.getStreet());
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Create an Exception given Request DTO validations are incorrect")
    void createNewWarehouse_throwNewException_whenInvalidParameters() {
        String city = "";
        String district = "";
        String state = "";
        String postalCode = "";
        String street = "";

        WarehouseRequest newWarehouseRequest = WarehouseRequest
                .builder()
                .city(city)
                .street(street)
                .state(state)
                .postalCode(postalCode)
                .district(district).build();

        Set<ConstraintViolation<WarehouseRequest>> violations = validator.validate(newWarehouseRequest);

        assertEquals(10, violations.size());
    }

    @Test
    @DisplayName("Returns a Warehouse from Storage.")
    void getById_returnsWarehouse_WhenSuccess() throws WarehouseNotFoundException {
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

        WarehouseModel newWarehouseEntity = newWarehouseRequest.toModel();

        Mockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(newWarehouseEntity));

        WarehouseModel responseWarehouse = warehouseService.getById(ArgumentMatchers.anyLong());


        assertEquals(city, responseWarehouse.getCity());
        assertEquals(district, responseWarehouse.getDistrict());
        assertEquals(state, responseWarehouse.getState());
        assertEquals(postalCode, responseWarehouse.getPostalCode());
        assertEquals(street, responseWarehouse.getStreet());

    }
    @Test
    @DisplayName("Throw exception when ID is not found.")
    void getById_throwsException_WhenIdIsInvalid() {

        Mockito.when(warehouseRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class, () -> warehouseService.getById(ArgumentMatchers.anyLong()));

    }

    @Test
    @DisplayName("Returns a List of Warehouse from Storage")
    void getAll_returnsWarehouseList_WhenSuccess() {
        String city1 = "Tramandaí";
        String district1 = "Zona Nova";
        String state1 = "Rio Grande do Sul";
        String postalCode1 = "99999999";
        String street1 = "Avenida Emancipacao";

        String city2 = "Tramandaí";
        String district2 = "Zona Nova";
        String state2 = "Rio Grande do Sul";
        String postalCode2 = "99999999";
        String street2 = "Avenida Emancipacao";

        WarehouseRequest newWarehouseRequest1 = WarehouseRequest
                .builder()
                .city(city1)
                .street(street1)
                .state(state1)
                .postalCode(postalCode1)
                .district(district1).build();

        WarehouseModel newWarehouseEntity1 = newWarehouseRequest1.toModel();

        WarehouseRequest newWarehouseRequest2 = WarehouseRequest
                .builder()
                .city(city2)
                .street(street2)
                .state(state2)
                .postalCode(postalCode2)
                .district(district2).build();

        WarehouseModel newWarehouseEntity2 = newWarehouseRequest2.toModel();


        warehouseService.save(newWarehouseEntity1);
        warehouseService.save(newWarehouseEntity2);

        List<WarehouseModel> expectedList = new ArrayList<>();
        expectedList.add(newWarehouseEntity1);
        expectedList.add(newWarehouseEntity2);

        Mockito.when(warehouseService.getAll())
                .thenReturn(expectedList);

        List<WarehouseModel> responseGetAll = warehouseService.getAll();


        assertEquals(2, responseGetAll.size());
    }

}