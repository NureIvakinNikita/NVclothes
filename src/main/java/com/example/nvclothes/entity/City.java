package com.example.nvclothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "city", schema = "project")
@Access(AccessType.FIELD)
@Builder
@AllArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "City_seq")
    @SequenceGenerator(name = "City_seq", sequenceName = "City_seq", allocationSize = 1, initialValue = 6)
    @Column
    private Long id;

    @Column
    private String name;

   /* @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostOffice> postOffices;*/
}
