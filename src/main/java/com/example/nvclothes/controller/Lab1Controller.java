package com.example.nvclothes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class Lab1Controller {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/whoMadeThis")
    public ModelAndView whoMadeIt(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("whoMadeThis");
        String bana54321bana3024 = bCryptPasswordEncoder.encode("Nikita1");
        log.info(bana54321bana3024);
        return modelAndView;
    }
}
