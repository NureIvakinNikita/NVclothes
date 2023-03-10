package com.example.nvclothes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_client")
//@IdClass(value = OrderClientEntityPK.class)
@Access(AccessType.FIELD)
public class OrderClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_client_id")
    private Long orderClientId;

    @Column(name = "client_id")
    private Long clientId;


    @Column(name = "order_group_id")
    private Long orderGroupId;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "order_group_id", referencedColumnName = "order_group_id", insertable = false, updatable = false),
            @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private OrderEntity order;

}

