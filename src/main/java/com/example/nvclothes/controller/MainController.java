package com.example.nvclothes.nvclothes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/all-products")
    public String mainPage(){
        return "allProducts";
    }
}
