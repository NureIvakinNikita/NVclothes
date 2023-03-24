package com.example.nvclothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "receipt", schema = "project")
@Access(AccessType.FIELD)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long receiptId;

    @Column
    private Long money;

    @Column
    private Date paymentDate;

    @Column
    private String address;

    @Column
    private String recipient;

    @Column
    private Long orderId;

    @Column
    private Long orderGroupId;

    @OneToMany(mappedBy = "receipt")
    private Set<OrderEntity> order;

}
