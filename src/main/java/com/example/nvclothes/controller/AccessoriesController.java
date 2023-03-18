package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.AccessoriesEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class AccessoriesController {

    @Autowired
    private AccessoriesEntityService accessoriesService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @GetMapping("/accessories")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            Collections.copy(searchedList, productList);
        } else {
            productList.addAll(accessoriesService.getAllAccessoriesEntities());
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        modelAndView.setViewName("accessories");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/accessories/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
        if (searchedList == null){
            searchedList.addAll(accessoriesService.getAllAccessoriesEntities());
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        searchedList = accessoriesService.filter(searchedList, size, costFrom, costTo,brand,productType);
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/accessories/clear")
    public ModelAndView clearFilters(){
        searchedList.addAll(accessoriesService.getAllAccessoriesEntities());
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/accessories/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        productList.addAll(accessoriesService.getAllAccessoriesEntities());
        accessoriesService.sortProducts(productList, sortType);
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("accessories");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/accessories/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        accessoriesService.addToCart(productId, productType);
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("accessories");
        List<Product> productList = new ArrayList<>();
        productList.addAll(accessoriesService.getAllAccessoriesEntities());
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
}
