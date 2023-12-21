package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_author", nullable = false)
    private Long id;

    @Column(name = "author_pseudonym", nullable = true, length = 32)
    private String pseudonym;

    @Column(name = "ID_country", nullable = true)
    private Long countryId;

    @Column(name = "author_first_name", nullable = true, length = 32)
    private String firstName;

    @Column(name = "author_last_name", nullable = true, length = 32)
    private String lastName;

    @Column(name = "author_patronymic", nullable = true, length = 32)
    private String patronymic;

    @Column(name = "author_book_amount", nullable = true)
    private Integer bookAmount;

}
