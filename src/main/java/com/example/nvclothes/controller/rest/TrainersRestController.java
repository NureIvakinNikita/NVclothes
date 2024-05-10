package com.example.nvclothes.controller.rest;


import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.TrainersEntityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TrainersRestController {
    @Autowired
    private TrainersEntityService trainersService;

    private List<Product> searchedList = null;

    @Autowired
    private FilterObject filterObject;

    @GetMapping("/api/trainers")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList.addAll(trainersService.getAllTrainersEntities());
        }


        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/trainers/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = trainersService.searchWithFilter(searchedList, name);
        } else {
            searchedList = trainersService.searchProducts(name);
        }

        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/trainers/filtered")
    public ResponseEntity<?> filterProducts(@Valid @ModelAttribute FilterObject filterObject, BindingResult result) {

        if (filterObject.getCostFrom() > filterObject.getCostTo()){
            Map<String, String> errors = new HashMap<>();
            errors.put("costFromBiggerError", "CostFrom can not be bigger than CostTo.");
            return ResponseEntity.badRequest().body(errors);
        }

        if (result.hasErrors()){
            Map<String, String> errors = ControllerUitls.getErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }

        List<Product> searchedList = new ArrayList<>();
        searchedList.addAll(trainersService.getAllTrainersEntities());
        searchedList = trainersService.filter(searchedList, filterObject);

        return new ResponseEntity<>(searchedList, HttpStatus.OK);
    }


    @PostMapping("/api/trainers/clear")
    public ResponseEntity<List<Product>> clearFilters(){
        searchedList = new ArrayList<>();
        searchedList.addAll(trainersService.getAllTrainersEntities());
        filterObject = FilterObject.builder().build();
        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/trainers/sort")
    public ResponseEntity<List<Product>> sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList == null){
            productList.addAll(trainersService.getAllTrainersEntities());
        } else {
            productList.addAll(searchedList);
        }
        trainersService.sortProducts(productList, sortType);

        return ResponseEntity.ok(productList);
    }
}