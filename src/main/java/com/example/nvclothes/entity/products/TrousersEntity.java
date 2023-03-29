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
@Table(name = "trousers", schema = "project")
@AllArgsConstructor
@Builder
@Access(AccessType.FIELD)
public class TrousersEntity extends Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "trousers_seq")
    @SequenceGenerator(name = "trousers_seq", sequenceName = "trousers_seq", allocationSize = 1, initialValue = 21)
    @Column
    private Long id;

    @Column(name = "trousers_id")
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

    @OneToMany(mappedBy = "trousers")
    private List<CartProductEntity> cartProductEntities;
}
