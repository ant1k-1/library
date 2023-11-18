package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_genre", nullable = false)
    private Long id;

    @Column(name = "genre_name", nullable = true, length = 32)
    private String name;
}
