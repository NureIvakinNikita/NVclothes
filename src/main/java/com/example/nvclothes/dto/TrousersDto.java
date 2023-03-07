package com.example.nvclothes.nvclothes.dto;

import com.example.nvclothes.nvclothes.model.Size;
import lombok.Data;

@Data
public class TrousersDto {

    private Long id;
    private Long trousersId;
    private String attribute;
    private String value;
    private String brand;
    private String name;
    private Long cost;
    private Size size;
    private Long amount;


    //private OrderEntity order;
}
