package com.example.nvclothes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_product")
@IdClass(value = OrderClientEntityPK.class)
@Access(AccessType.FIELD)
public class OrderProduct {

    @Id
    private Long orderProductId;

    private Long orderId;

    private Long productId;

    private 
}
