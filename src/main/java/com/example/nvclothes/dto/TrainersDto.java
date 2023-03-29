package com.example.nvclothes.dto;

import com.example.nvclothes.model.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainersDto {

    private Long id;
    private Long trainersId;
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
