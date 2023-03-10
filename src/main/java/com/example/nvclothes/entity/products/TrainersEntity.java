package com.example.nvclothes.entity.products;

import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trainers", schema = "project")
@AllArgsConstructor
@Builder
@Access(AccessType.FIELD)
public class TrainersEntity extends Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long trainersId;

    @Column
    private String attribute;

    @Column
    private String value;

    private String brand;

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
}
