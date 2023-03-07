package com.example.nvclothes.nvclothes.entity.products;

import com.example.nvclothes.nvclothes.entity.OrderEntity;
import com.example.nvclothes.nvclothes.model.Size;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trousers", schema = "project")
@AllArgsConstructor
@Builder
public class TrousersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long trousersId;

    @Column
    private String attribute;

    @Column
    private String value;

    private String brand;

    private String name;

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
}
