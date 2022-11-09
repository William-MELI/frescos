package com.meli.frescos.service;

import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.IWarehouseRepository;
import org.aspectj.lang.annotation.Before;
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
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator=  factory.getValidator();
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

        WarehouseModel newWarehouseEntity = newWarehouseRequest.toEntity();

        Set<ConstraintViolation<WarehouseRequest>> violations = validator.validate(newWarehouseRequest);
        Mockito.when(warehouseRepository.save(ArgumentMatchers.any())).thenReturn(newWarehouseEntity);


        WarehouseModel responseWarehouse = warehouseService.create(newWarehouseEntity);

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
    void getById_returnsWarehouse_WhenSuccess() {
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

        WarehouseModel newWarehouseEntity = newWarehouseRequest.toEntity();

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

        assertThrows(NullPointerException.class, () -> {
            WarehouseModel responseWarehouse = warehouseService.getById(ArgumentMatchers.anyLong());
        });

    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}