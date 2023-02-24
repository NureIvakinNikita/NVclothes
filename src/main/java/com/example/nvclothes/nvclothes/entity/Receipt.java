package com.example.nvclothes.nvclothes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "receipt", schema = "nvclothes")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long receiptId;

    @Column
    private Long money;

    @Column
    private Date paymentDate;

    private String address;

    private String client;

}
