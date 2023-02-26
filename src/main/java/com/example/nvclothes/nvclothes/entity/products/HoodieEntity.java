package com.example.nvclothes.nvclothes.entity.products;


import com.example.nvclothes.nvclothes.entity.Order;
import com.example.nvclothes.nvclothes.model.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "hoodie", schema = "project")
@EqualsAndHashCode
public class Hoodie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long hoodieId;

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
    private Order order;
}
