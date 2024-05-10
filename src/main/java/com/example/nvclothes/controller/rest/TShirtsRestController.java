package com.example.nvclothes.controller.rest;


import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.TShirtEntityService;
import jakarta.validation.Valid;
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
public class TShirtsRestController {

    @Autowired
    private TShirtEntityService tShirtService;

    private List<Product> searchedList = null;

    @Autowired
    private FilterObject filterObject;


    @GetMapping("/api/t-shirts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList.addAll(tShirtService.getAllTShirtEntities());
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/t-shirts/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = tShirtService.searchWithFilter(searchedList, name);
        } else {
            searchedList = tShirtService.searchProducts(name);
        }

        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/t-shirts/filtered")
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
        searchedList.addAll(tShirtService.getAllTShirtEntities());
        searchedList = tShirtService.filter(searchedList, filterObject);

        return new ResponseEntity<>(searchedList, HttpStatus.OK);
    }

    @PostMapping("/api/t-shirts/clear")
    public ResponseEntity<List<Product>> clearFilters(){
        searchedList = new ArrayList<>();
        searchedList.addAll(tShirtService.getAllTShirtEntities());
        filterObject = FilterObject.builder().build();
        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/t-shirts/sort")
    public ResponseEntity<List<Product>> sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList == null){
            productList.addAll(tShirtService.getAllTShirtEntities());
        } else {
            productList.addAll(searchedList);
        }
        tShirtService.sortProducts(productList, sortType);

        return ResponseEntity.ok(productList);
    }
}