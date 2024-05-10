package com.example.nvclothes.controller.rest;


import com.example.nvclothes.controller.ControllerUitls;
import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.AccessoriesEntityService;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class AccessoriesRestController {
    @Autowired
    private IAccessoriesEntityService accessoriesService;

    private List<Product> searchedList = null;

    @Autowired
    private FilterObject filterObject;

    @GetMapping("/api/accessories")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            Collections.copy(searchedList, productList);
        } else {
            productList.addAll(accessoriesService.getAllAccessoriesEntities());
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/accessories/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = accessoriesService.searchWithFilter(searchedList, name);
        } else {
            searchedList = accessoriesService.searchProducts(name);
        }

        List<Product> productList = new ArrayList<>(searchedList);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/accessories/filtered")
    public ResponseEntity<?> filterProducts(@Valid @ModelAttribute FilterObject filterObject, BindingResult result) {

        if (filterObject.getCostFrom() > filterObject.getCostTo()){
            Map<String, String> errors = new HashMap<>();
            errors.put("costFromBiggerError", "CostFrom can not be bigger than CostTo.");
            return ResponseEntity.badRequest().body(errors);
        }

        if (result!=null && result.hasErrors()){
            Map<String, String> errors = ControllerUitls.getErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }

        List<Product> searchedList = new ArrayList<>();
        searchedList.addAll(accessoriesService.getAllAccessoriesEntities());
        searchedList = accessoriesService.filter(searchedList, filterObject);

        return new ResponseEntity<>(searchedList, HttpStatus.OK);
    }

   @PostMapping("/api/accessories/clear")
    public ResponseEntity<List<Product>> clearFilters(){
       searchedList = new ArrayList<>();
       searchedList.addAll(accessoriesService.getAllAccessoriesEntities());
       filterObject = FilterObject.builder().build();
       List<Product> productList = new ArrayList<>(searchedList);

       return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/api/accessories/sort")
    public ResponseEntity<List<Product>> sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList == null){
            productList.addAll(accessoriesService.getAllAccessoriesEntities());
        } else {
            productList.addAll(searchedList);
        }
        accessoriesService.sortProducts(productList, sortType);

        return ResponseEntity.ok(productList);
    }

}
