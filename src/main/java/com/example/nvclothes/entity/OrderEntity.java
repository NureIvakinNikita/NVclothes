package com.example.nvclothes.nvclothes.entity;

import com.example.nvclothes.nvclothes.entity.products.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "orders", schema = "project")
public class OrderEntity {

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
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "receipt_id" , nullable = true, referencedColumnName = "id")
    private ReceiptEntity receipt;

    @OneToOne(mappedBy = "order")
    private TrainersEntity product;

    @OneToOne(mappedBy = "order")
    private TrousersEntity trousers;

    @OneToOne(mappedBy = "order")
    private TShirtEntity tShirt;

    @OneToOne(mappedBy = "order")
    private HoodieEntity hoodie;

    @OneToOne(mappedBy = "order")
    private AccessoriesEntity accessories;

}
