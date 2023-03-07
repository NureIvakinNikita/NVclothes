package com.example.nvclothes.nvclothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "client", schema = "project")
@Builder
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String telephoneNumber;
    @Column
    private Date birthday;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private CartEntity cart_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "order_client",
            joinColumns =
                    { @JoinColumn(name = "client_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "order_id", referencedColumnName = "id") })
    private OrderEntity order;
}
