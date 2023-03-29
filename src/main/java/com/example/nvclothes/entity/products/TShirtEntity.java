package com.example.nvclothes.entity.products;


import com.example.nvclothes.entity.CartProductEntity;
import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_shirt", schema = "project")
@AllArgsConstructor
@Builder
@Access(AccessType.FIELD)
public class TShirtEntity extends Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tShirt_seq")
    @SequenceGenerator(name = "tShirt_seq", sequenceName = "tShirt_seq", allocationSize = 1, initialValue = 21)
    @Column
    private Long id;

    @Column(name = "t_shirt_id")
    private Long productId;

    @Column
    private String attribute;

    @Column
    private String value;

    private String photo;

    private Brand brand;

    private String name;

    private ProductType productType;

    private Long cost;

    private Size size;

    private Long amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "order_product",
            joinColumns =
                    { @JoinColumn(name = "product_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "order_id", referencedColumnName = "id") })
    private OrderEntity order;

    @OneToMany(mappedBy = "tShirt")
    private List<CartProductEntity> cartProductEntities;
}
