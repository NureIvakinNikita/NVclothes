package com.example.nvclothes.entity.products;

import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Product {


    private Long productId;
    private String photo;
    private Brand brand;
    private String name;
    private Long cost;
    private Size size;
    private Long amount;

    private ProductType productType;
}
