package com.example.nvclothes.controller;

import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.OrderProductEntity;
import com.example.nvclothes.entity.ReceiptEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.exception.CartNotFoundException;
import com.example.nvclothes.service.CartEntityService;
import com.example.nvclothes.service.CartProductEntityService;
import com.example.nvclothes.service.OrderEntityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.*;

@Controller
@Slf4j
public class CartController {

    @Autowired
    private CartEntityService cartEntityService;

    @Autowired
    private CartProductEntityService cartProductEntityService;

    @Autowired
    private OrderEntityService orderEntityService;

    private HashMap<Long, Long> productsAmount;

    @GetMapping("/user/cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public ModelAndView cart(){
        List<Product> productList = new ArrayList<>();
        
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
            RedirectView redirectView = new RedirectView("/all-products");
            ModelAndView modelAndView = new ModelAndView(redirectView);
            return modelAndView;
        }
        CartEntity cart = CartEntity.builder().build();
        try{
            cart = cartEntityService.getCartEntityByClientId(userId);
        } catch (CartNotFoundException e){
            log.info(e.toString());
        }

        productList.addAll(cartEntityService.getClientProducts(userId, cart.getId()));
        productsAmount = new HashMap<>();
        ListIterator<Product> iterator = productList.listIterator();
        Product product;
        while (iterator.hasNext()){
            product = iterator.next();
            if (productsAmount.containsKey(product.getProductId())){
                productsAmount.put(product.getProductId(),productsAmount.get(product.getProductId()) + 1L);
                iterator.remove();
            } else {
                productsAmount.put(product.getProductId(),1L);
            }
        }

        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("productsAmount", productsAmount);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("totalCost", cart.getPrice());
        return modelAndView;
    }

    @PostMapping("/user/cart/remove")
    public ModelAndView remove(@RequestParam("productType") String productType,
                               @RequestParam("productId") String productId){
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
            RedirectView redirectView = new RedirectView("/all-products");
            ModelAndView modelAndView = new ModelAndView(redirectView);
            return modelAndView;
        }
        CartEntity cart = CartEntity.builder().build();
        try{
            cart = cartEntityService.getCartEntityByClientId(userId);
        } catch (CartNotFoundException e){
            log.info(e.toString());
        }

        cartEntityService.removeFromCart(cart, productType, productId);
        RedirectView redirectView = new RedirectView("/user/cart");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }

   /* @PostMapping("/user/cart")
    public String order(){


        return "redirect:/all-products";
    }*/

    @PostMapping("/all-products/add-to-cart")
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_USER')")
    public String addToCart(@RequestParam("productId") Long productId,
                                  @RequestParam("productType") String productType, HttpServletRequest request){
        /*HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");*/


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        cartEntityService.addToCart(productId, productType, userId);


        return "redirect:/all-products";

    }

    @PostMapping("/user/cart/order")
    public ModelAndView order(@RequestParam("city") String city, @RequestParam("posht") String posht,
                              @RequestParam("payment") String payment, @RequestParam("recipient") String recipient){

        OrderEntity order = OrderEntity.builder()
                .registrationDate(new Date())
                .address(city+posht)
        .build();
        List<Product> productList = new ArrayList<>();
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
            RedirectView redirectView = new RedirectView("/all-products");
            ModelAndView modelAndView = new ModelAndView(redirectView);
            return modelAndView;
        }
        CartEntity cart = CartEntity.builder().build();
        try{
            cart = cartEntityService.getCartEntityByClientId(userId);
        } catch (CartNotFoundException e){
            log.info(e.toString());
        }
        productList.addAll(cartEntityService.getClientProducts(userId, cart.getId()));
        orderEntityService.createOrder(order, productList, userId, recipient);
        cartEntityService.deleteAllByCartId(cart.getId());
        cart.setPrice(0L);
        cart.setProductAmount(0L);
        cartEntityService.save(cart);
        RedirectView redirectView = new RedirectView("/all-products");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }



}
