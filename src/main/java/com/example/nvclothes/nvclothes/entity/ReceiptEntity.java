package com.example.nvclothes.nvclothes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "receipt", schema = "project")
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

    @Column
    private String address;


    //private String client;

    @OneToMany(mappedBy = "receipt")
    private Set<Order> order;

}
