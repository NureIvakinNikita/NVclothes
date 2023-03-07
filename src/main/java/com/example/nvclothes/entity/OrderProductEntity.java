package com.example.nvclothes.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import org.aspectj.weaver.ast.Or;

@Entity
@Table(name = "order_product")
@IdClass(value = OrderClientEntityPK.class)
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


    private OrderEntity order;

    private ClientEntity client;
}
