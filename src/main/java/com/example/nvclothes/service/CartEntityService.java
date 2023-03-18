package com.example.nvclothes.service;


import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.exception.AccessoryNotFoundException;
import com.example.nvclothes.exception.CartNotFoundException;
import com.example.nvclothes.repository.interfaces.CartEntityRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartEntityService {

    @Autowired
    private CartEntityRepositoryInterface cartRepository;

    public CartEntity getCartEntityById(@Param("id") Long id) throws CartNotFoundException{
        Optional<CartEntity> cartOptional = cartRepository.getCartEntityById(id);
        if (!cartOptional.isPresent()) {
            throw new CartNotFoundException("Accessory not found for id: " + id);
        }
        return cartOptional.get();
    }

    public CartEntity getCartEntityByClientId(@Param("id") Long clientId) throws CartNotFoundException{
        Optional<CartEntity> cartOptional = cartRepository.getCartEntityByClientId(clientId);
        if (!cartOptional.isPresent()) {
            throw new CartNotFoundException("Accessory not found for id: " + clientId);
        }
        return cartOptional.get();
    }

    void deleteCartEntityById(@Param("id") Long id){
        cartRepository.deleteCartEntityById(id);
    }

    public void save(CartEntity cart){
        cartRepository.save(cart);
    }


}
