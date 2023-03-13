package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.AllProductsServices;
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
public class AllProductsController {

    @Autowired
    private AllProductsServices allProductsServices;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @GetMapping("/all-products")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView){
            List<Product> productList = new ArrayList<>();
            if (searchedList != null) {
                Collections.copy(searchedList, productList);
            } else {
                productList = allProductsServices.getAllProducts();
            }
            modelAndView.setViewName("allProducts");
            modelAndView.addObject("productList", productList);
            return modelAndView;
    }

    @PostMapping("/all-products/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = allProductsServices.searchWithFilter(searchedList, name);
        } else {
            searchedList = allProductsServices.searchProducts(name);
        }

        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/all-products/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("cost") String cost,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
        if (searchedList == null){
            searchedList = allProductsServices.getAllProducts();
        }
        searchedList = allProductsServices.filter(searchedList, size, cost,brand,productType);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/all-products/clear")
    public ModelAndView clearFilters(){
        searchedList = allProductsServices.getAllProducts();
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/all-products/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = allProductsServices.getAllProducts();
        allProductsServices.sortProducts(productList, sortType);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @PostMapping("/all-products/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId, @RequestParam("productType") String productType){
        allProductsServices.addToCart(productId, productType);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", allProductsServices.getAllProducts());
        return modelAndView;
    }

}
