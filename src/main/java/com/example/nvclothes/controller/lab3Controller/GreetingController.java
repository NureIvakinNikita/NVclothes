package com.example.nvclothes.controller.lab3Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class GreetingController {

    @GetMapping("/")
    public String getMainPage(){
        return "mainPage";
    }

    @GetMapping("/greeting")
    public String getGreeting(){
        return "greeting";
    }
}
