package com.example.nvclothes.service.interfaces;


import jakarta.validation.OverridesAttribute;
import org.springframework.stereotype.Service;

@Service
public interface IAdminService {

    boolean validateData(String name, Long cost, Long amount, String size);

    boolean edit(Long productId, String productType, String brand, String name, Long cost, Long amount, String size);

    boolean delete(Long productId, String productType);
    boolean addProduct(String photo, String brand, String name, Long cost, String size, Long amount, String type);

}
