package com.meli.frescos.controller;

import com.meli.frescos.controller.dto.*;
import com.meli.frescos.model.OrderProductsModel;
import com.meli.frescos.service.IOrderProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @RestController to OrderProducts
 */
@RestController
@RequestMapping("/orderProducts")
public class OrderProductsController {

    private final IOrderProductService iservice;

    public OrderProductsController(IOrderProductService service) {
        this.iservice = service;
    }

    /**
     * Return all OrderProducts
     * Return 200 OK when operation is success
     * @return a list with all OrderProducts instance
     */
    @GetMapping
    public ResponseEntity<List<OrderProductsResponse>> getAll() throws Exception {
        List<OrderProductsResponse> orderProductResponseList = new ArrayList<>();
        for (OrderProductsModel order : iservice.getAll()) {
            orderProductResponseList.add(OrderProductsResponse.toResponse(order));
        }
        return new ResponseEntity<>(orderProductResponseList ,HttpStatus.OK);
    }

    /**
     * Creates a new OrderProducts instance.
     * Returns 201 CREATED when operation is success
     *
     * @param order the OrderProducts instance
     * @return a OrderProducts instance
     */
    @PostMapping
    public ResponseEntity<OrderProductsResponse> save(@RequestBody @Valid OrderProductsRequest order) {
        OrderProductsModel insertOrderProduct = iservice.save(order);
        return new ResponseEntity<>(OrderProductsResponse.toResponse(insertOrderProduct), HttpStatus.CREATED);
    }

    /**
     * Return a OrderProducts given id
     * Return 200 OK when operation is success
     * @param id the OrderProducts ID
     * @return the OrderProducts instance related id
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderProductsResponse> getById(@PathVariable Long id) throws Exception {
        OrderProductsModel order = iservice.getById(id);
        return new ResponseEntity<>(OrderProductsResponse.toResponse(order), HttpStatus.OK);
    }

    @GetMapping("/idOrder")
    public ResponseEntity<List<OrderProductsResponse>> getByPurchaseId(@PathVariable Long idOrder) throws Exception {
        List<OrderProductsModel> orderByPurchase = iservice.getByPurchaseId(idOrder);
        List<OrderProductsResponse> orderProductsResponses = new ArrayList<>();
        orderByPurchase.forEach(o -> orderProductsResponses.add(OrderProductsResponse.toResponse(o)));
        return new ResponseEntity<>(orderProductsResponses, HttpStatus.OK);
    }
}
