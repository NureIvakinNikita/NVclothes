package com.example.nvclothes.controller;

import com.example.nvclothes.config.TokenProvider;
import com.example.nvclothes.dto.ClientDto;
import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.exception.ClientNotFoundException;
import com.example.nvclothes.mappers.ClientEntityMapper;
import com.example.nvclothes.service.CartEntityService;
import com.example.nvclothes.service.ClientEntityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class RegistrationController {

    @Autowired
    private ClientEntityService clientEntityService;

    @Autowired
    private CartEntityService cartEntityService;

    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/registration")
    public ModelAndView registration() {
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("role", role);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    /*@PostMapping("/registration/create")
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
            ClientEntity client = ClientEntity.builder().build();
            try {
                client= clientEntityService.getClientByEmail(email);
            } catch (ClientNotFoundException e){

            }


            CartEntity cart = CartEntity.builder()
                    .clientId(client.getId())
                    .price(0L)
                    .productAmount(0L)
                    .build();
            cartEntityService.save(cart);
        }
        else return "redirect:/registration";

        return "redirect:/all-products";
    }*/

    @PostMapping("/registration/create")
    public ModelAndView createClient(@Valid ClientDto clientDto, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");

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
        modelAndView.addObject("role", role);
        modelAndView.addObject("userId", userId);

        //clientEntityService.deleteClientById(11L);

        if (!clientDto.getPassword().equals(clientDto.getPassword2())){
            modelAndView.addObject("passwordError", "Passwords are different");
            return modelAndView;
        }

        try{
            if (clientEntityService.getClientByEmail(clientDto.getEmail()) != null){
                modelAndView.addObject("usernameError", "User exists!");
                return modelAndView;
            }
        } catch (ClientNotFoundException e){
            log.info(e.toString());
        }

        if (result.hasErrors()){
            Map<String, String> errors = ControllerUitls.getErrors(result);
            modelAndView.addAllObjects(errors);
            return modelAndView;
        } else {
            ClientEntity clientEntity = ClientEntityMapper.INSTANCE.toEntity(clientDto);
            clientEntityService.save(clientEntity);
            ClientEntity client = ClientEntity.builder().build();
            try {
                client= clientEntityService.getClientByEmail(clientDto.getEmail());
            } catch (ClientNotFoundException e){
                log.info(e.toString());
                return modelAndView;
            }
            CartEntity cart = CartEntity.builder()
                    .clientId(client.getId())
                    .price(0L)
                    .productAmount(0L)
                    .build();
            cartEntityService.save(cart);
        }

        RedirectView redirectView = new RedirectView("/all-products");
        modelAndView = new ModelAndView(redirectView);
        return modelAndView;

    }



    @GetMapping("/login")
    public ModelAndView login(){
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request) {

        ClientEntity clientEntity;
        if (clientEntityService.validateCredentials(email, password)) {
            try {
                clientEntity = clientEntityService.getClientByEmail(email);
            } catch (ClientNotFoundException e) {
                return "redirect:/registration";
            }

            String token = tokenProvider.createToken(clientEntity.getId(), email);
            HttpSession session = request.getSession();
            session.setAttribute("token", token);

            log.info("User " + email + " successfully logged in with token " + token);

            return "redirect:/all-products";
        } else {
            return "redirect:/registration";
        }
    }

    @PostMapping("/log-out")
    public String logout(HttpServletRequest request) {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        //SecurityContextHolder.setContext(null);
        HttpSession session = request.getSession();
        session.removeAttribute("token");
        authentication =SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/all-products";
    }
}
