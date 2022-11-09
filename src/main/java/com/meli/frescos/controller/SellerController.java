package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.SellerRequest;
import com.meli.frescos.controller.dto.SellerResponse;
import com.meli.frescos.service.ISellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final ISellerService service;

    @PostMapping()
    public ResponseEntity<SellerResponse> save(@RequestBody SellerRequest sellerRequest){
        return new ResponseEntity<>(SellerResponse.toResponse(service.save(sellerRequest.toEntity())), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<SellerResponse>> findAll(){
        List<SellerResponse> sellerResponseList = service.findAll().stream().map(s -> SellerResponse.toResponse(s)).toList();
        return new ResponseEntity<>(sellerResponseList,HttpStatus.OK);
    }

    @GetMapping("/filter-id")
    public ResponseEntity<SellerResponse> findById(@RequestParam Long id){
        return new ResponseEntity<>(SellerResponse.toResponse(service.findById(id)) , HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<SellerResponse> update(@RequestBody SellerRequest sellerRequest, @RequestParam Long id){
        return new ResponseEntity<>(SellerResponse.toResponse(service.update(sellerRequest.toEntity(), id)), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(Long id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
