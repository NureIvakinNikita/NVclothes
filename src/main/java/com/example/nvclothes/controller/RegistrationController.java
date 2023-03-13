package com.example.nvclothes.controller;

import com.example.nvclothes.config.TokenProvider;
import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.exception.ClientNotFoundException;
import com.example.nvclothes.service.ClientEntityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private ClientEntityService clientEntityService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration/create")
    public String createClient(@RequestParam("name") String name, @RequestParam("email") String email,
                               @RequestParam("password") String password, @RequestParam("lastName") String lastName,
                               @RequestParam("telephoneNumber") String telephoneNumber, @RequestParam("password2") String password2) {
        try{
            if (clientEntityService.getClientByEmail(email) != null){
                //error
                return "redirect:/login";
            }
        } catch (ClientNotFoundException e){
            ///
        }




        if (clientEntityService.validateClientEntity(name, lastName, email, password, telephoneNumber, password2)){
            ClientEntity clientEntity = ClientEntity.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .lastName(lastName)
                    .telephoneNumber(telephoneNumber)
                    .build();
            clientEntityService.save(clientEntity);
        }
        else return registration();

        return "redirect:/all-products";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request) {

        ClientEntity clientEntity;
        if (clientEntityService.validateCredentials(email, password)){
            try{
                 clientEntity = clientEntityService.getClientByEmail(email);
            } catch (ClientNotFoundException e){
                return "redirect:/registration";
            }

            String token = tokenProvider.createToken(clientEntity.getId(), email);
            HttpSession session = request.getSession();
            session.setAttribute("token", token);

            return "redirect:/all-products";
        } else {
            return "redirect:/registration";
        }
    }
}
