package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.controller.dto.WarehouseResponse;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("warehouse")
public class WarehouseController {

    @Autowired
    IWarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseModel> create(@RequestBody WarehouseRequest request){
        this.warehouseService.create(request.toEntity());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<WarehouseResponse> getById(@PathVariable Long id){
        WarehouseModel warehouseEntity = this.warehouseService.getById(id);
        WarehouseResponse warehouseResponse = WarehouseResponse.toResponse(warehouseEntity);

        return new ResponseEntity<>(warehouseResponse, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<WarehouseModel> getAll(){

    }

}
