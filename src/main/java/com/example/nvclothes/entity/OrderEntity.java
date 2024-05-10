package com.example.nvclothes.entity;

import com.example.nvclothes.entity.products.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders", schema = "project")
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "order_group_id")
    private Long orderGroupId;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date registrationDate;

    @Column
    private String address;

    @Column
    private Long clientId;

//    @Column
//    private Long receiptId;


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
