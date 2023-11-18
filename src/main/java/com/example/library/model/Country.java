package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_country", nullable = false)
    private Long id;

    @Column(name = "country_name", nullable = true, length = 20)
    private String name;
}
