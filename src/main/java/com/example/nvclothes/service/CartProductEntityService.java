package com.example.nvclothes.service;

import com.example.nvclothes.entity.CartProductEntity;
import com.example.nvclothes.repository.interfaces.CartProductEntityRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductEntityService {

    @Autowired
    private CartProductEntityRepositoryInterface cartProductEntityRepository;

    public List<CartProductEntity> getCartProductEntitiesByCartId(Long id){
        List<CartProductEntity> cartProductEntities = cartProductEntityRepository.getCartProductEntitiesByCartId(id);
        return cartProductEntities;
    }

    public void addProductToCart(Long productId, Long cartId, String productType){
        CartProductEntity cartProductEntity = CartProductEntity.builder()
                .productId(productId)
                .cartId(cartId)
                .productType(productType).build();
        cartProductEntityRepository.save(cartProductEntity);
    }

}
