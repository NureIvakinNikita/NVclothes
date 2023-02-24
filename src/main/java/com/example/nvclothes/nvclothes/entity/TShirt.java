package com.example.nvclothes.nvclothes.entity;


import com.example.nvclothes.nvclothes.model.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tShirt", schema = "nvclothes")
public class TShirt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long tShirtId;

    @Column
    private String attribute;

    @Column
    private String value;

    private String brand;

    private String name;

    private Long cost;

    private Size size;

    private Long amount;
}
