package com.example.nvclothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart", schema = "project")
@Access(AccessType.FIELD)
@Builder
@AllArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq_gen")
    @SequenceGenerator(name = "my_seq_gen", sequenceName = "MY_SEQUENCE", allocationSize = 3)
    @Column
    private Long id;
    @Column(name = "product_amount")
    private Long productAmount;
    @Column(name = "price")
    private Long price;
    @Column(name = "client_id")
    private Long clientId;

    @OneToOne(mappedBy = "cart_id")
    private ClientEntity client;

    @OneToMany(mappedBy = "cart")
    private List<CartProductEntity> products;

}
