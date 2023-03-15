package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.HoodieEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class HoodiesController {
    @Autowired
    private HoodieEntityService hoodiesServices;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @GetMapping("/hoodies")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            Collections.copy(searchedList, productList);
        } else {
            productList.addAll(hoodiesServices.getAllHoodieEntities());
        }
        modelAndView.setViewName("hoodies");
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @PostMapping("/hoodies/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = hoodiesServices.searchWithFilter(searchedList, name);
        } else {
            searchedList = hoodiesServices.searchProducts(name);
        }

        ModelAndView modelAndView = new ModelAndView("hoodies");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/hoodies/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
        if (searchedList == null){
            searchedList.addAll(hoodiesServices.getAllHoodieEntities());
        }
        searchedList = hoodiesServices.filter(searchedList, size, costFrom, costTo,brand,productType);
        ModelAndView modelAndView = new ModelAndView("hoodies");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/hoodies/clear")
    public ModelAndView clearFilters(){
        searchedList.addAll(hoodiesServices.getAllHoodieEntities());
        ModelAndView modelAndView = new ModelAndView("hoodies");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/hoodies/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        productList.addAll(hoodiesServices.getAllHoodieEntities());
        hoodiesServices.sortProducts(productList, sortType);
        ModelAndView modelAndView = new ModelAndView("hoodies");
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @PostMapping("/hoodies/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        hoodiesServices.addToCart(productId, productType);
        ModelAndView modelAndView = new ModelAndView("hoodies");
        List<Product> productList = new ArrayList<>();
        productList.addAll(hoodiesServices.getAllHoodieEntities());
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }
}
