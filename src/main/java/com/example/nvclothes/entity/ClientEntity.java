package com.example.nvclothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "client", schema = "project")
@Builder
@AllArgsConstructor
@Access(AccessType.FIELD)
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column
    private Date birthday;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartEntity cart_id;

    @OneToMany(mappedBy = "client")
    private List<OrderClientEntity> orders;


}
