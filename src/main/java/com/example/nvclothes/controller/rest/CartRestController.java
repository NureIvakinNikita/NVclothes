package com.example.nvclothes.controller.rest;

import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.City;
import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.exception.CartNotFoundException;
import com.example.nvclothes.service.*;
import com.example.nvclothes.service.interfaces.IOrderEntityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@RestController
@Slf4j
public class CartRestController {

    @Autowired
    private CartEntityService cartEntityService;

    @Autowired
    private CartProductEntityService cartProductEntityService;

    @Autowired
    private IOrderEntityService orderEntityService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ClientEntityService clientEntityService;

    @Autowired
    private CityService cityService;

    @Autowired
    private PostOfficeService postOfficeService;

    private HashMap<String, Long> productsAmount;

    @GetMapping("/api/user/cart")
    public ResponseEntity<Map<String, Object>> getCart(Long userId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> productList = new ArrayList<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e) {
            userId = null;
        }
        CartEntity cart = CartEntity.builder().build();
        try {
            cart = cartEntityService.getCartEntityByClientId(userId);
        } catch (CartNotFoundException e) {
            log.info(e.toString());
        }

        productList.addAll(cartEntityService.getClientProducts(userId, cart.getId()));
        productsAmount = new HashMap<>();
        ListIterator<Product> iterator = productList.listIterator();
        Product product;
        while (iterator.hasNext()) {
            product = iterator.next();
            if (productsAmount.containsKey(product.getProductId() + "" + product.getProductType())) {
                productsAmount.put(product.getProductId() + "" + product.getProductType(), productsAmount.get(product.getProductId() + "" + product.getProductType()) + 1L);
                iterator.remove();
            } else {
                productsAmount.put(product.getProductId() + "" + product.getProductType(), 1L);
            }
        }

        response.put("productList", productList);
        response.put("productsAmount", productsAmount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/user/cart/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam("productType") String productType,
                                                              @RequestParam("productId") String productId) {
        Map<String, Object> response = new HashMap<>();
        Long userId = null;
        String role;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e) {
            userId = null;
        }

        CartEntity cart = CartEntity.builder().build();
        try {
            cart = cartEntityService.getCartEntityByClientId(userId);
        } catch (CartNotFoundException e) {
            log.info(e.toString());
        }

        cartEntityService.removeFromCart(cart, productType, productId);
        response.put("redirect", "/user/cart");
        return ResponseEntity.ok("/api/user/cart");
    }

    @PostMapping("/api/all-products/add-to-cart")
    public ResponseEntity<Object> addToCart(@RequestParam("productId") Long productId,
                                            @RequestParam("productType") String productType,
                                            HttpServletRequest request) {
        Long userId = null;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e) {
            userId = null;
            ResponseEntity.badRequest().build();
        }

        cartEntityService.addToCart(productId, productType, userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/user/cart/order")
    public ResponseEntity<String> order(@RequestParam("city") String city, @RequestParam("posht") String posht,
                              @RequestParam("payment") String payment, @RequestParam("recipient") String recipient){

        OrderEntity order = OrderEntity.builder()
                .registrationDate(new Date())
                .address(posht)
                .build();
        List<Product> productList = new ArrayList<>();
        Long userId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = Long.parseLong(authentication.getName());
        } catch (Exception e){
            userId = null;
        }
        CartEntity cart = CartEntity.builder().build();
        try{
            cart = cartEntityService.getCartEntityByClientId(userId);
        } catch (CartNotFoundException e){
            log.info(e.toString());
        }
        productList.addAll(cartEntityService.getClientProducts(userId, cart.getId()));
        if (productList != null && productList.size()>0) {
            orderEntityService.createOrder(order, productList, userId, recipient);
            cartEntityService.deleteAllByCartId(cart.getId());
            cart.setPrice(0L);
            cart.setProductAmount(0L);
            cartEntityService.save(cart);
        } else {
            return ResponseEntity.badRequest().body("/all-products");
        }

        return ResponseEntity.ok().body("/all-products");
    }
}