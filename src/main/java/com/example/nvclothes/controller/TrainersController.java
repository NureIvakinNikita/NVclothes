package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.TrainersEntityService;
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
public class TrainersController {
    @Autowired
    private TrainersEntityService trainersService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @GetMapping("/trainers")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList.addAll(trainersService.getAllTrainersEntities());
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        modelAndView.setViewName("trainers");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/trainers/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = trainersService.searchWithFilter(searchedList, name);
        } else {
            searchedList = trainersService.searchProducts(name);
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }

        ModelAndView modelAndView = new ModelAndView("trainers");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/trainers/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("cost") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
        if (searchedList == null){
            searchedList.addAll(trainersService.getAllTrainersEntities());
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        searchedList = trainersService.filter(searchedList, size, costFrom, costTo,brand,productType);
        ModelAndView modelAndView = new ModelAndView("trainers");

        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/trainers/clear")
    public ModelAndView clearFilters(){
        searchedList.addAll(trainersService.getAllTrainersEntities());
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("trainers");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/trainers/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        productList.addAll(trainersService.getAllTrainersEntities());
        trainersService.sortProducts(productList, sortType);
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("trainers");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/trainers/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        trainersService.addToCart(productId, productType);
        ModelAndView modelAndView = new ModelAndView("trainers");
        List<Product> productList = new ArrayList<>();
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        productList.addAll(trainersService.getAllTrainersEntities());
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
}
