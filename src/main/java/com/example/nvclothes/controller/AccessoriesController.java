package com.example.nvclothes.controller;

import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.service.AccessoriesEntityService;
import com.example.nvclothes.service.interfaces.IAccessoriesEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class AccessoriesController {


    @Autowired
    private AccessoriesEntityService accessoriesService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;
    @Autowired
    private FilterObject filterObject;

    @GetMapping("/accessories")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            Collections.copy(searchedList, productList);
        } else {
            productList.addAll(accessoriesService.getAllAccessoriesEntities());
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
        modelAndView.setViewName("accessories");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        modelAndView.addObject("brandList", brandList);
        return modelAndView;
    }

    @PostMapping("/accessories/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = accessoriesService.searchWithFilter(searchedList, name);
        } else {
            searchedList = accessoriesService.searchProducts(name);
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
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("filter", filterObject);
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping("/accessories/filtered")
    public ModelAndView filterProducts(@Valid FilterObject filterObject, BindingResult result
                                        /*@RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                        @RequestParam("brand") String brand*/){
        ModelAndView modelAndView = new ModelAndView("accessories");

        if (searchedList == null){
            searchedList = new ArrayList<>();
            searchedList.addAll(accessoriesService.getAllAccessoriesEntities());
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

        searchedList = accessoriesService.filter(searchedList, filterObject);


        return modelAndView;
    }

    @PostMapping("/accessories/clear")
    public ModelAndView clearFilters(){
        searchedList = new ArrayList<>();
        searchedList.addAll(accessoriesService.getAllAccessoriesEntities());
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
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);

        return modelAndView;
    }

    @PostMapping("/accessories/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList!= null){
            productList = searchedList;
        } else {
            productList.addAll(accessoriesService.getAllAccessoriesEntities());
        }
        accessoriesService.sortProducts(productList, sortType);
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
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }

    @PostMapping("/accessories/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        accessoriesService.addToCart(productId, productType);
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
        ModelAndView modelAndView = new ModelAndView("accessories");
        List<Product> productList = new ArrayList<>();
        productList.addAll(accessoriesService.getAllAccessoriesEntities());
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }
}
