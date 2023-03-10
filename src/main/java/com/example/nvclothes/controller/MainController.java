package com.example.nvclothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

   /* @GetMapping("/all-products")
    public String mainPage(){
        return "allProducts";
    }*/

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
