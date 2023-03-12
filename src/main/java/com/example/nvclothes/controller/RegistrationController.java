package com.example.nvclothes.controller;

import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.service.ClientEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private ClientEntityService clientEntityService;

    @GetMapping("/no-auth/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/no-auth/registration/create")
    public String createClient(@RequestParam("name") String name, @RequestParam("email") String email,
                               @RequestParam("password") String password, @RequestParam("lastName") String lastName,
                               @RequestParam("telephoneNumber") String telephoneNumber, @RequestParam("password2") String password2) {


        return "registration";
    }
}
