package com.example.nvclothes.dto;

import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.entity.products.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    private Long id;
    private Long orderGroupId;
    private Date registrationDate;
    private String productType;
    private String address;
    private ClientDto clientDto;
    /*@ManyToOne
    @JoinColumn(name = "receipt_id" , nullable = true, referencedColumnName = "id")
    private ReceiptEntity receipt;*/
    private TrainersDto trainersDto;
    private TrousersDto trousersDto;
    private TShirtDto tShirtDto;
    private HoodieDto hoodieDto;
    private AccessoriesDto accessoriesDto;
}
