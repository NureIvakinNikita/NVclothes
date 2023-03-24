package com.example.nvclothes.repository.interfaces;


import com.example.nvclothes.entity.CartProductEntity;
import jakarta.transaction.Transactional;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductEntityRepositoryInterface extends JpaRepository<CartProductEntity, Long> {

    List<CartProductEntity> getCartProductEntitiesByCartId(Long id);

    void deleteCartProductEntityByProductIdAndProductType(Long productId, String productType);

    @Transactional
    void deleteAllByCartId(Long cartId);
    @Transactional
    void deleteCartProductEntityByCartProductId(Long id);
    void removeCartProductEntityByCartProductId(Long cartProductId);

}
