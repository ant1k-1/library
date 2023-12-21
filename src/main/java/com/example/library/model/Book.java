package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_book", nullable = false)
    private Long id;

    @Column(name = "book_title", nullable = true, columnDefinition = "TEXT")
    private String title;

    @Column(name = "book_year", nullable = true)
    private Integer year;

    @Column(name = "book_amount", nullable = true)
    private Integer amount;

    @Column(name = "ID_publishment", nullable = true)
    private Long publishmentId;

    @Column(name = "ID_author", nullable = true)
    private Long authorId;

    @OneToMany
    @CollectionTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"))
    private Set<Genre> genres;
}
