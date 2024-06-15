package com.example.nvclothes.controller;

import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.OrderProductEntity;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import com.example.nvclothes.service.interfaces.IOrderEntityService;
import com.example.nvclothes.service.interfaces.IOrderProductEntityService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Lab2Controller {

    @Autowired
    private IAccessoriesEntityService accessoriesEntityService;

    @Autowired
    private IOrderEntityService orderEntityService;

    @Autowired
    private IOrderProductEntityService orderProductEntityService;



    @GetMapping("/lab2/all-accessories")
    public ResponseEntity<List<AccessoriesEntity>> getAllAccessories(){
        return new ResponseEntity<>(accessoriesEntityService.getAllAccessoriesEntities(), HttpStatus.OK);
    }

    @GetMapping("/lab2/all-orders")
    public ResponseEntity<List<OrderEntity>> getAllOrders(){
        return new ResponseEntity<>(orderEntityService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/lab2/all-orders-products")
    public ResponseEntity<List<OrderProductEntity>> getAllOrdersAndProducts(){
        List<OrderProductEntity> orderProductEntities = orderProductEntityService.findAll();
        System.out.println(orderProductEntities);
        return new ResponseEntity<>(orderProductEntities, HttpStatus.OK);

    }


}
