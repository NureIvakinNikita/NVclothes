package com.example.nvclothes.controller;

import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.repository.interfaces.ClientEntityRepositoryInterface;
import com.example.nvclothes.repository.interfaces.HoodieEntityRepositoryInterface;
import com.example.nvclothes.repository.interfaces.TrainersEntityRepositoryInterface;
import com.example.nvclothes.service.AllProductsServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class AllProductsController {

    @Autowired
    private AllProductsServices allProductsServices;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private FilterObject filterObject;

    private List<Product> searchedList = null;

    /*@GetMapping("/all-products")
    public ModelAndView*//*ResponseEntity<String>*//* getAllProducts(ModelAndView modelAndView, HttpServletRequest request){


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
        if (searchedList != null) {
            productList.addAll(searchedList);
        } else {
            productList = allProductsServices.getAllProducts();
        }
        modelAndView.setViewName("allProducts");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("role", role);
            //log.info(session.getAttribute("token").toString());
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }*/

    @GetMapping("/all-products")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView, HttpServletRequest request){
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
        modelAndView.setViewName("allProducts");
        modelAndView.addObject("role", role);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("filter", filterObject);
        modelAndView.addObject("brandList", brandList);
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


        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }



    @PostMapping("/all-products/filtered")
    public ModelAndView filterProducts(@Valid FilterObject filterObject, BindingResult result){
        ModelAndView modelAndView = new ModelAndView("allProducts");

        if (searchedList == null){
            searchedList = allProductsServices.getAllProducts();
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

        searchedList = allProductsServices.filter(searchedList, filterObject);


        return modelAndView;
    }

    @PostMapping("/all-products/clear")
    public ModelAndView clearFilters(){
        searchedList = allProductsServices.getAllProducts();
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
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }

    @PostMapping("/all-products/sort")
    public ModelAndView sortProducts(@RequestParam("sortType") String sortType){
        List<Product> productList = new ArrayList<>();
        if (searchedList!=null) {
            productList = searchedList;
        } else {
            productList = allProductsServices.getAllProducts();
        }
        allProductsServices.sortProducts(productList, sortType);
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
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
        return modelAndView;
    }



}
