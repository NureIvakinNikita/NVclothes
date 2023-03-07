package com.example.nvclothes.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart", schema = "project")
@Access(AccessType.FIELD)
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long productAmount;
    @Column
    private Long price;
    @Column
    private Long clientId;

    @OneToOne(mappedBy = "cart_id")
    private ClientEntity client;


}
