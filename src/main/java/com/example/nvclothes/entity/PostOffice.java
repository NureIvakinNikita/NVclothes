package com.example.nvclothes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post_office", schema = "project")
@Access(AccessType.FIELD)
@Builder
@AllArgsConstructor
public class PostOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Post_office_seq")
    @SequenceGenerator(name = "Post_office_seq", sequenceName = "Post_office_seq", allocationSize = 1, initialValue = 6)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name ="city_id")
    private Long cityId;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;*/
}
