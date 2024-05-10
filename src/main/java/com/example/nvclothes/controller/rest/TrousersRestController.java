package com.example.nvclothes.controller.rest;


import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.TrousersEntityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class TrousersRestController {
    @Autowired
    private TrousersEntityService trousersService;

    private List<Product> searchedList = null;

    @Autowired
    private FilterObject filterObject;

    @GetMapping("/api/trousers")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList.addAll(trousersService.getAllTrousersEntities());
        }


        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/trousers/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = trousersService.searchWithFilter(searchedList, name);
        } else {
            searchedList = trousersService.searchProducts(name);
        }

        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/trousers/filtered")
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
        searchedList.addAll(trousersService.getAllTrousersEntities());
        searchedList = trousersService.filter(searchedList, filterObject);

        return new ResponseEntity<>(searchedList, HttpStatus.OK);
    }

    @PostMapping("/api/trousers/clear")
    public ResponseEntity<List<Product>> clearFilters(){
        searchedList = new ArrayList<>();
        searchedList.addAll(trousersService.getAllTrousersEntities());
        filterObject = FilterObject.builder().build();
        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/trousers/sort")
    public ResponseEntity<List<Product>> sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList == null){
            productList.addAll(trousersService.getAllTrousersEntities());
        } else {
            productList.addAll(searchedList);
        }
        trousersService.sortProducts(productList, sortType);

        return ResponseEntity.ok(productList);
    }
}