package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "book_log")
public class BookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
    @Column(name = "ID_card", nullable = false)
    private Long cardId;

//    @Id
    @Column(name = "ID_book", nullable = false)
    private Long bookId;

//    @Id
    @Column(name = "ID_libstaff", nullable = false)
    private Long libstaffId;

    @Column(name = "log_date_from", nullable = false)
    private java.sql.Date dateFrom;

    @Column(name = "log_date_until", nullable = false)
    private java.sql.Date dateUntil;

    @Column(name = "log_status", nullable = false)
    private Integer status;
}
