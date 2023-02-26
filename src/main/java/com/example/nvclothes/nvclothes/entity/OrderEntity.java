package com.example.nvclothes.nvclothes.entity;

import com.example.nvclothes.nvclothes.entity.products.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "orders", schema = "project")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Id
    private Long orderGroupId;

    @Column
    private Date registrationDate;

    @Column
    private String productType;

    @Column
    private String address;


    @OneToOne(mappedBy = "order")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "receipt_id" , nullable = true, referencedColumnName = "id")
    private Receipt receipt;

    @OneToOne(mappedBy = "order")
    private Trainers product;

    @OneToOne(mappedBy = "order")
    private Trousers trousers;

    @OneToOne(mappedBy = "order")
    private TShirt tShirt;

    @OneToOne(mappedBy = "order")
    private Hoodie hoodie;

    @OneToOne(mappedBy = "order")
    private Accessories accessories;

}
