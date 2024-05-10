package com.example.nvclothes.controller;

import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.service.TrousersEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TrousersController {
    @Autowired
    private TrousersEntityService trousersService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @Autowired
    private FilterObject filterObject;

    @GetMapping("/trousers")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList.addAll(trousersService.getAllTrousersEntities());
        }
        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        List<String> brandList =  Arrays.stream(Brand.values()).map(Brand::getDisplayName).collect(Collectors.toList());
        modelAndView.setViewName("trousers");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        modelAndView.addObject("brandList", brandList);
        return modelAndView;
    }

    @PostMapping("/trousers/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = trousersService.searchWithFilter(searchedList, name);
        } else {
            searchedList = trousersService.searchProducts(name);
        }
        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }

    @PostMapping("/trousers/filtered")
    public ModelAndView filterProducts(@Valid FilterObject filterObject, BindingResult result/* @RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand*/){
        ModelAndView modelAndView = new ModelAndView("trousers");
        if (searchedList == null){
            searchedList = new ArrayList<>();
            searchedList.addAll(trousersService.getAllTrousersEntities());
        }

        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }

        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);

        if (filterObject.getCostFrom() > filterObject.getCostTo()){
            modelAndView.addObject("costFromBiggerError", "CostFrom can not be bigger than CostTo.");
            return modelAndView;
        }

        if (result.hasErrors()){
            Map<String, String> errors = ControllerUitls.getErrors(result);
            modelAndView.addAllObjects(errors);
            return modelAndView;
        }
        searchedList = trousersService.filter(searchedList,filterObject);


        return modelAndView;
    }

    @PostMapping("/trousers/clear")
    public ModelAndView clearFilters(){
        searchedList = new ArrayList<>();
        searchedList.addAll(trousersService.getAllTrousersEntities());
        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        filterObject = FilterObject.builder().build();
        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }

    @PostMapping("/trousers/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        productList.addAll(trousersService.getAllTrousersEntities());
        trousersService.sortProducts(productList, sortType);
        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }

    @PostMapping("/trousers/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        trousersService.addToCart(productId, productType);
        ModelAndView modelAndView = new ModelAndView("trousers");
        Long userId;
        String role;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
            if (authentication.getAuthorities().size() == 1){
                role = "ROLE_USER";
            } else {
                role = "ROLE_ADMIN";
            }
        } catch (Exception e){
            userId = null;
            role = "null";
        }
        List<Product> productList = new ArrayList<>();
        productList.addAll(trousersService.getAllTrousersEntities());
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }
}
