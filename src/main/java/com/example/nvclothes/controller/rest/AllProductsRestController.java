package com.example.nvclothes.controller.rest;

import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.AllProductsServices;
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
public class AllProductsRestController {

    @Autowired
    private AllProductsServices allProductsServices;

    @Autowired
    private FilterObject filterObject;

    private List<Product> searchedList = null;

    @GetMapping("/api/all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList = allProductsServices.getAllProducts();
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/all-products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = allProductsServices.searchWithFilter(searchedList, name);
        } else {
            searchedList = allProductsServices.searchProducts(name);
        }

        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/all-products/filtered")
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

        List<Product> searchedList = allProductsServices.getAllProducts();
        searchedList = allProductsServices.filter(searchedList, filterObject);

        return new ResponseEntity<>(searchedList, HttpStatus.OK);
    }

    @PostMapping("/api/all-products/clear")
    public ResponseEntity<List<Product>> clearFilters(){
        searchedList = allProductsServices.getAllProducts();
        filterObject = FilterObject.builder().build();
        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/all-products/sort")
    public ResponseEntity<List<Product>> sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList!=null) {
            productList = allProductsServices.sortProducts(searchedList, sortType);
        } else {
            searchedList = allProductsServices.getAllProducts();
            productList = allProductsServices.sortProducts(searchedList, sortType);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
