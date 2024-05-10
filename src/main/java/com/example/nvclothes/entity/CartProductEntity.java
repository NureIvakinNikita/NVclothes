package com.example.nvclothes.entity;

import com.example.nvclothes.entity.products.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_product", schema = "public")
@Access(AccessType.FIELD)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long cartProductId;

    @Column(name = "cart_id")
    private Long cartId;


    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_type")
    private String productType;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CartEntity cart;


    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HoodieEntity hoodie;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TrainersEntity trainers;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TShirtEntity tShirt;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TrousersEntity trousers;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AccessoriesEntity accessory;

}
