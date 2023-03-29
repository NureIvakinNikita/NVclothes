package com.example.nvclothes.controller;

import com.example.nvclothes.controller.sortObjects.FilterObject;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.*;
import com.example.nvclothes.service.AllProductsServices;
import jakarta.servlet.Filter;
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

    @Autowired
    private TrainersEntityRepositoryInterface trainersRepository;

    @Autowired
    private HoodieEntityRepositoryInterface hoodieRepository;

    @Autowired
    private ClientEntityRepositoryInterface clientEntityRepositoryInterface;

    @Autowired
    private FilterObject filterObject;

    private List<Product> searchedList = null;

    @GetMapping("/all-products")
    public ModelAndView/*ResponseEntity<String>*/ getAllProducts(ModelAndView modelAndView, HttpServletRequest request){
        //clientEntityRepositoryInterface.deleteClientEntityById(8L);
        //hoodieRepository.deleteHoodieEntityById(24L);

        /*HttpSession session = request.getSession();
        String token;
        try{
            token = session.getAttribute("token").toString();
            log.info(token);
        } catch (Exception e){
            session.setAttribute("token", "");
            token = "";
        }*/

        /*TrainersEntity trainersEntity = TrainersEntity.builder().build();

        trainersEntity.setId(24L*//*trainersEntity.getId()+1L*//*);
        trainersEntity.setProductId(5L);
        String size = Size.US_8.getDisplayName();
        trainersEntity.setAttribute(Attribute.SIZE.getDisplayName());
        trainersEntity.setValue(size);
        trainersEntity.setPhoto("http://res.cloudinary.com/dyoibiowd/image/upload/c_fill,h_380,w_255/trainers_5");
        trainersRepository.save(trainersEntity);*/

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
    public ModelAndView filterProducts(@RequestParam("productType") String productType, @RequestParam("costFrom") String costFrom,
                                       @RequestParam("costTo") String costTo,
                                       @RequestParam("size") String size, @RequestParam("brand") String brand){
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
        Long cF = 0L, cT = 0L;
        if (!costFrom.equals("")) cF = Long.parseLong(costFrom);
        if (!costTo.equals("")) cT = Long.parseLong(costTo);
        filterObject = FilterObject.builder()
                .size(size)
                .costFrom(cF)
                .costTo(cT)
                .brand(brand)
                .productType(productType).build();

        searchedList = allProductsServices.filter(searchedList, filterObject/*size, costFrom, costTo ,brand,productType*/);
        ModelAndView modelAndView = new ModelAndView("allProducts");
        modelAndView.addObject("productList", searchedList);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("filter", filterObject);
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
