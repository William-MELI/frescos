package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.controller.dto.WarehouseResponse;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.service.IWarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("warehouse")
public class WarehouseController {

    private final IWarehouseService warehouseService;

    public WarehouseController(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> save(@RequestBody WarehouseRequest request){
        return new ResponseEntity<>(WarehouseResponse.toResponse(this.warehouseService.save(request.toModel())), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getById(@PathVariable Long id){
        WarehouseModel warehouseEntity = this.warehouseService.getById(id);
        WarehouseResponse warehouseResponse = WarehouseResponse.toResponse(warehouseEntity);

        return new ResponseEntity<>(warehouseResponse, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAll(){
    List<WarehouseResponse> warehouseResponseList = this.warehouseService.getAll().stream().map(WarehouseResponse::toResponse).toList();
    return new ResponseEntity<>(warehouseResponseList,HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        this.warehouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
