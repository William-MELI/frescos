package com.meli.frescos.service;

import com.meli.frescos.exception.OneToOneMappingAlreadyDefinedException;
import com.meli.frescos.exception.RepresentativeNotFoundException;
import com.meli.frescos.exception.RepresentativeWarehouseNotAssociatedException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.repository.RepresentativeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RepresentativeServiceTest {

    @InjectMocks
    RepresentativeService representativeService;

    @Mock
    RepresentativeRepository representativeRepository;

    @Mock
    IWarehouseService warehouseService;

    @Test
    @DisplayName("Create a new Representative successfully")
    void saveNewRepresentative_returnCreatedRepresentative_whenSuccess() throws WarehouseNotFoundException {
        String name = "Representative name";
        Long warehouse_id = 1L;
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        RepresentativeModel responseModel = RepresentativeModel
                .builder()
                .name(name)
                .build();

        Mockito.when(warehouseService.getById(warehouse_id)).thenReturn(warehouseResponse);
        Mockito.when(representativeRepository.findRepresentativeModelByWarehouseId(warehouse_id)).thenReturn(null);
        Mockito.when(representativeRepository.save(ArgumentMatchers.any(RepresentativeModel.class))).thenReturn(responseModel);

        RepresentativeModel responseRepresentative = representativeService.save(responseModel, warehouse_id);

        assertEquals(name, responseRepresentative.getName());
        assertEquals(warehouse_id, responseRepresentative.getWarehouse().getId());
    }

    @Test
    @DisplayName("Throw exception when Warehouse Id already defined with representative")
    void saveNewRepresentantive_throwsException_OneToOneMappingAlreadyDefinedException() throws WarehouseNotFoundException {
        String name = "Representative name";
        Long warehouse_id = 1L;
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        RepresentativeModel responseModel = RepresentativeModel
                .builder()
                .name(name)
                .build();

        Mockito.when(warehouseService.getById(warehouse_id)).thenReturn(warehouseResponse);
        Mockito.when(representativeRepository.findRepresentativeModelByWarehouseId(warehouse_id)).thenReturn(new RepresentativeModel());

        assertThrows(OneToOneMappingAlreadyDefinedException.class, () -> {
            representativeService.save(responseModel, warehouse_id);
        });
    }

    @Test
    @DisplayName("Returns a Representative by ID")
    void getById_returnRepresentative_WhenSuccess() throws RepresentativeNotFoundException {
        String name = "Representative name";
        Long warehouse_id = 1L;
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        RepresentativeModel responseModel = RepresentativeModel
                .builder()
                .name(name)
                .warehouse(warehouseResponse)
                .build();

        Mockito.when(representativeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(responseModel));

        RepresentativeModel responseRepresentative = representativeService.getById(ArgumentMatchers.anyLong());

        assertEquals(name, responseRepresentative.getName());
        assertEquals(warehouse_id, responseRepresentative.getWarehouse().getId());
    }

    @Test
    @DisplayName("Throws RepresentativeNotFoundException when ID does not exists")
    void getById_throwsRepresentativeNotFoundException_WhenRepresentativeDoesNotExists() {

        Long id = -1L;

        Mockito.when(representativeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RepresentativeNotFoundException.class, () -> {
            representativeService.getById(id);
        });

    }

    @Test
    @DisplayName("Returns a All Representative")
    void getAll_returnListOfRepresentative_whenSuccess() {
        WarehouseModel warehouse = new WarehouseModel("blumenau", "Santa Catarina", "15 de Maio", "5662sdww", "aaaaa");

        List<RepresentativeModel> representativeList = new ArrayList<>();

        representativeList.add(new RepresentativeModel(1L, "Representante 1", warehouse));
        representativeList.add(new RepresentativeModel(2L, "Representantee 2", warehouse));

        Mockito.when(representativeRepository.findAll())
                .thenReturn(representativeList);

        List<RepresentativeModel> responseRepresentative = representativeService.getAll();

        assertEquals(representativeList, responseRepresentative);
        assertEquals(2, responseRepresentative.size());
    }

    @Test
    @DisplayName("Representative belong to the warehouse")
    void validateRepresentative_whenSuccess() throws RepresentativeWarehouseNotAssociatedException, RepresentativeNotFoundException, WarehouseNotFoundException {
        Long warehouse_id = 1L;
        Long representative_id = 1L;
        String name = "Representative 1";
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        RepresentativeModel representativeModel = new RepresentativeModel()
                .builder()
                .id(representative_id)
                .name(name)
                .warehouse(new WarehouseModel()
                        .builder()
                        .id(warehouse_id)
                        .build()
                ).build();

        Mockito.when(representativeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(representativeModel));

        representativeService.validateRepresentative(representative_id, warehouse_id);
    }

    @Test
    @DisplayName("Throw when Representative does not belong to the warehouse")
    void validateRepresentative_throwException() {
        Long warehouse_id = 1L;
        Long representative_id = 1L;
        String name = "Representative 1";
        WarehouseModel warehouseResponse = new WarehouseModel();
        warehouseResponse.setId(warehouse_id);

        RepresentativeModel representativeModel = new RepresentativeModel()
                .builder()
                .id(representative_id)
                .name(name)
                .warehouse(new WarehouseModel()
                        .builder()
                        .id(warehouse_id)
                        .build()
                ).build();

        Mockito.when(representativeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(representativeModel));

        assertThrows(RepresentativeWarehouseNotAssociatedException.class, () -> {
            representativeService.validateRepresentative(representative_id, 2L);
        });
    }
}