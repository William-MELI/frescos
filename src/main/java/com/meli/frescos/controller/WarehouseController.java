package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.WarehouseRequest;
import com.meli.frescos.controller.dto.WarehouseResponse;
import com.meli.frescos.model.WarehouseModel;
import com.meli.frescos.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<WarehouseResponse>> getAll(){
    List<WarehouseModel> warehouseModelList = this.warehouseService.getAll();
    List<WarehouseResponse> warehouseResponseList = WarehouseResponse.toResponse(warehouseModelList);

    return new ResponseEntity<>(warehouseResponseList,HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.warehouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
