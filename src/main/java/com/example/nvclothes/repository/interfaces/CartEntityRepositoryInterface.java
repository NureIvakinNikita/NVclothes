package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.CartEntity;
import com.example.nvclothes.entity.ClientEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartEntityRepositoryInterface extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> getCartEntityById(@Param("id") Long id);

    Optional<CartEntity> getCartEntityByClientId(@Param("id") Long clientId);

    @Transactional
    void deleteCartEntityById(@Param("id") Long id);
    @Transactional
    void deleteCartEntityByClientId(@Param("client_id") Long clientId);



}
