package com.example.nvclothes.nvclothes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "order", schema = "nvclothes")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Id
    private Long orderGroupId;

    @Column
    private Date registrationDate;

    @Column
    private String productType;

    @Column
    private String address;
}
