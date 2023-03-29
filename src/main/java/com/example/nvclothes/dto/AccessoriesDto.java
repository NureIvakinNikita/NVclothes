package com.example.nvclothes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessoriesDto {


    private Long id;
    private Long accessoriesId;
    private String attribute;
    private String value;
    private String photo;
    private String brand;
    private String name;
    private Long cost;
    private Long amount;
    private OrderDto orderDto;
}
