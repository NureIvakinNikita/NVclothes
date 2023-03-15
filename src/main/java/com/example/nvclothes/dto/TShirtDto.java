package com.example.nvclothes.dto;

import com.example.nvclothes.model.Size;
import lombok.Data;

@Data
public class TShirtDto {

    private Long id;
    private Long tShirtId;
    private String attribute;
    private String value;
    private String photo;
    private String brand;
    private String name;
    private Long cost;
    private Size size;
    private Long amount;

    private OrderDto orderDto;
}
