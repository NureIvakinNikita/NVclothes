package com.example.nvclothes.entity;

import com.example.nvclothes.entity.products.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders", schema = "project")
@Access(AccessType.FIELD)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "order_group_id")
    private Long orderGroupId;

    @Column
    private Date registrationDate;

    @Column
    private String productType;

    @Column
    private String address;


    @OneToMany(mappedBy = "order")
    private List<OrderClientEntity> orderClientList;

    @ManyToOne
    @JoinColumn(name = "receipt_id" , nullable = true, referencedColumnName = "id")
    private ReceiptEntity receipt;

    @OneToOne(mappedBy = "order")
    private TrainersEntity trainers;

    @OneToOne(mappedBy = "order")
    private TrousersEntity trousers;

    @OneToOne(mappedBy = "order")
    @Parameter(name = "tShirt")
    private TShirtEntity tShirt;

    @OneToOne(mappedBy = "order")
    private HoodieEntity hoodie;

    @OneToOne(mappedBy = "order")
    private AccessoriesEntity accessories;

}
