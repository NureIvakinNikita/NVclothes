package com.example.nvclothes.controller;

import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.service.AllProductsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

        /*List<Product> productList = new ArrayList<>();
        if (searchedList != null) {
            Collections.copy(searchedList, productList);
        } else {
            productList = allProductsServices.getAllProducts();
        }
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        String html = templateEngine.process("allProducts", new Context(Locale.getDefault(), modelAndView.getModel()));
        modelAndView.addObject("responseEntity", new ResponseEntity<>(html, HttpStatus.OK));
        return modelAndView;*/
    }

    /*@GetMapping("/all-products/search")
    public ModelAndView searchProducts(@RequestParam("searchTerm") String searchTerm, WebRequest request) {
        List<Product> productList = allProductsServices.searchProducts(searchTerm);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        String html = templateEngine.process("allProducts", new Context(Locale.getDefault(), modelAndView.getModel()));
        modelAndView.addObject("responseEntity", new ResponseEntity<>(html, HttpStatus.OK));
        return modelAndView;
    }*/

    @PostMapping("/all-products/search")
    public ModelAndView searchProducts(@RequestParam("search") String name) {
        searchedList = allProductsServices.searchProducts(name);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

    @PostMapping("/all-products/filtered")
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("cost") String cost,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){

        Iterator<Product> iterator = searchedList.iterator();

        while (iterator.hasNext()){
            Product product = iterator.next();
            if (!(product.getSize().equals(size) || size.equals("All") &&
            product.getCost().equals(cost) || cost.equals("All") &&
            product.getBrand().equals(brand) || brand.equals("All") &&
            product.getProductType().equals(productType) || productType.equals("All"))){
                searchedList.remove(product);
            }
        }
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        return modelAndView;
    }

   /* @GetMapping("/all-products/search")
    public ResponseEntity<String> searchProducts(@RequestParam("searchTerm") String searchTerm, WebRequest webRequest) {
        List<Product> productList = allProductsServices.searchProducts(searchTerm);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        String html = templateEngine.process("allProducts", new StandardContext(webRequest, modelAndView.getModel()));
        return new ResponseEntity<>(html, HttpStatus.OK);
    }*/

    /*@GetMapping("/all-products/search")
    public ModelAndView searchProducts(@RequestParam("searchTerm") String searchTerm, WebRequest request) {
        List<Product> productList = allProductsServices.searchProducts(searchTerm);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", productList);
        String html = templateEngine.process("allProducts", new StandardExpressionContext(new ThymeleafEvaluationContext(request, templateEngine.getConfiguration()), modelAndView.getModel()));
        modelAndView.addObject("responseEntity", new ResponseEntity<>(html, HttpStatus.OK));
        return modelAndView;
    }*/







}
