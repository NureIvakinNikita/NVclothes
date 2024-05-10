package com.example.nvclothes.controller.rest;

import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.HoodieEntityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class HoodiesRestController {

    @Autowired
    private HoodieEntityService hoodiesServices;

    @Autowired
    private FilterObject filterObject;

    private List<Product> searchedList = null;

    @GetMapping("/api/hoodies")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList.addAll(hoodiesServices.getAllHoodieEntities());
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/hoodies/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = hoodiesServices.searchWithFilter(searchedList, name);
        } else {
            searchedList = hoodiesServices.searchProducts(name);
        }

        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/hoodies/filtered")
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
        searchedList.addAll(hoodiesServices.getAllHoodieEntities());
        searchedList = hoodiesServices.filter(searchedList, filterObject);

        return new ResponseEntity<>(searchedList, HttpStatus.OK);
    }

    @PostMapping("/api/hoodies/clear")
    public ResponseEntity<List<Product>> clearFilters(){
        searchedList = new ArrayList<>();
        searchedList.addAll(hoodiesServices.getAllHoodieEntities());
        filterObject = FilterObject.builder().build();
        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/hoodies/sort")
    public ResponseEntity<List<Product>> sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList == null){
            productList.addAll(hoodiesServices.getAllHoodieEntities());
        } else {
            productList.addAll(searchedList);
        }
        hoodiesServices.sortProducts(productList, sortType);

        return ResponseEntity.ok(productList);
    }


}
