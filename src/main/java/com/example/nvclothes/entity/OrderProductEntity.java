package com.example.nvclothes.entity;

import com.example.nvclothes.entity.products.*;
import jakarta.persistence.*;

@Entity
@Table(name = "order_product")
@Access(AccessType.FIELD)
public class OrderProductEntity {

    @Id
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

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "order_group_id", referencedColumnName = "order_group_id", insertable = false, updatable = false),
            @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private OrderEntity order;

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
    private AccessoriesEntity accessories;
}
