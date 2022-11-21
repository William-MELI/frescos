package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.controller.dto.WarehouseResponse;
import com.meli.frescos.exception.UsedPrimaryKeyConstraintException;
import com.meli.frescos.exception.WarehouseNotFoundException;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.service.IWarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All endpoints related to Warehouse
 * Is`s a Spring @RestController
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    private final IWarehouseService warehouseService;

    public WarehouseController(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * POST endpoint to store a {@link WarehouseModel}.
     * @param request
     * @return ResponseEntity<WarehouseResponse> with status 201 created
     */
    @PostMapping
    public ResponseEntity<WarehouseResponse> save(@Valid @RequestBody WarehouseRequest request) {
        return new ResponseEntity<>(WarehouseResponse.toResponse(this.warehouseService.save(request.toModel())), HttpStatus.CREATED);
    }

    /**
     * Endpoint to return a Warehouse given id
     * @param id the Warehouse id
     * @return a WarehouseModel related ID with status 200 ok
     * @throws WarehouseNotFoundException Throws in case Warehouse does not exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getById(@PathVariable Long id) throws WarehouseNotFoundException {
        WarehouseModel warehouseEntity = this.warehouseService.getById(id);
        WarehouseResponse warehouseResponse = WarehouseResponse.toResponse(warehouseEntity);

        return new ResponseEntity<>(warehouseResponse, HttpStatus.OK);
    }

    /**
     * Endpoint to return all Warehouse
     * @return a List with all Warehouse with status 200 ok
     */
    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAll() {
        List<WarehouseResponse> warehouseResponseList = this.warehouseService.getAll().stream().map(WarehouseResponse::toResponse).toList();
        return new ResponseEntity<>(warehouseResponseList, HttpStatus.OK);
    }

    /**
     * DELETE endpoint to store a {@link WarehouseModel}.
     * @param id the Warehouse id
     * @return ResponseEntity<Void> with status 200 ok
     * @throws UsedPrimaryKeyConstraintException Throws in case Warehouse is related with a Section
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws UsedPrimaryKeyConstraintException {
        this.warehouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
