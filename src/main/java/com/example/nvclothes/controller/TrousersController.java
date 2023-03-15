package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.TrousersEntityService;
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
public class TrousersController {
    @Autowired
    private TrousersEntityService trousersService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @GetMapping("/trousers")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
        List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            Collections.copy(searchedList, productList);
        } else {
            productList.addAll(trousersService.getAllTrousersEntities());
        }
        modelAndView.setViewName("trousers");
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @PostMapping("/trousers/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = trousersService.searchWithFilter(searchedList, name);
        } else {
            searchedList = trousersService.searchProducts(name);
        }

        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/trousers/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
        if (searchedList == null){
            searchedList.addAll(trousersService.getAllTrousersEntities());
        }
        searchedList = trousersService.filter(searchedList, size, costFrom, costTo,brand,productType);
        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/trousers/clear")
    public ModelAndView clearFilters(){
        searchedList.addAll(trousersService.getAllTrousersEntities());
        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/trousers/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        productList.addAll(trousersService.getAllTrousersEntities());
        trousersService.sortProducts(productList, sortType);
        ModelAndView modelAndView = new ModelAndView("trousers");
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @PostMapping("/trousers/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        trousersService.addToCart(productId, productType);
        ModelAndView modelAndView = new ModelAndView("trousers");
        List<Product> productList = new ArrayList<>();
        productList.addAll(trousersService.getAllTrousersEntities());
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }
}