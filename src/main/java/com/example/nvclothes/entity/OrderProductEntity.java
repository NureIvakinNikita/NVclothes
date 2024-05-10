package com.example.nvclothes.entity;

import com.example.nvclothes.entity.products.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "order_product", schema = "project")
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    @SequenceGenerator(name = "my_seq", sequenceName = "MY_SEQ", allocationSize = 1)
    @Column(name = "order_product_id")
    private Long orderProductId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_group_id")
    private Long orderGroupId;

    @Column(name = "product_type")
    private String productType;
    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "order_group_id", referencedColumnName = "order_group_id", insertable = false, updatable = false),
            @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private OrderEntity order;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HoodieEntity hoodie;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TrainersEntity trainers;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TShirtEntity tShirt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TrousersEntity trousers;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AccessoriesEntity accessories;
}
