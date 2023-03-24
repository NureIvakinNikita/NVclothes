package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.AllProductsServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
public class AllProductsController {

    @Autowired
    private AllProductsServices allProductsServices;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private List<Product> searchedList = null;

    @GetMapping("/all-products")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView, HttpServletRequest request){
        /*HttpSession session = request.getSession();
        String token;
        try{
            token = session.getAttribute("token").toString();
            log.info(token);
        } catch (Exception e){
            session.setAttribute("token", "");
            token = "";
        }*/

        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }



        List<Product> productList = new ArrayList<>();
            if (searchedList != null) {
               productList.addAll(searchedList);
            } else {
                productList = allProductsServices.getAllProducts();
            }
            modelAndView.setViewName("allProducts");
            modelAndView.addObject("productList", productList);
            //log.info(session.getAttribute("token").toString());
            modelAndView.addObject("userId", userId);
            return modelAndView;
    }

    @PostMapping("/all-products/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        if (searchedList != null) {
            searchedList = allProductsServices.searchWithFilter(searchedList, name);
        } else {
            searchedList = allProductsServices.searchProducts(name);
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }


        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/all-products/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
        if (searchedList == null){
            searchedList = allProductsServices.getAllProducts();
        }
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }

        searchedList = allProductsServices.filter(searchedList, size, costFrom, costTo ,brand,productType);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/all-products/clear")
    public ModelAndView clearFilters(){
        searchedList = allProductsServices.getAllProducts();
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/all-products/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = allProductsServices.getAllProducts();
        allProductsServices.sortProducts(productList, sortType);
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    /*@PostMapping("/all-products/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView addToCart(@RequestParam("productId") Long productId,
                                  @RequestParam("productType") String productType, HttpServletRequest request){
        *//*HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");*//*


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        allProductsServices.addToCart(productId, productType, userId);

        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", allProductsServices.getAllProducts());

        return modelAndView;

    }*/

}
