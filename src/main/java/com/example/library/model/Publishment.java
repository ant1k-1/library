package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "publishment")
public class Publishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_publishment", nullable = false)
    private Long id;

    @Column(name = "publishment_name", nullable = true, length = 64)
    private String name;

    @Column(name = "publishment_book_amount", nullable = true)
    private Integer bookAmount;
}
