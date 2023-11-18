package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "book_log")
public class BookLog {
    @Id
    @Column(name = "ID_card", nullable = false)
    private Long cardId;

    @Id
    @Column(name = "ID_book", nullable = false)
    private Long bookId;

    @Id
    @Column(name = "ID_libstaff", nullable = false)
    private Long libstaffId;

    @Column(name = "log_date_from", nullable = false)
    private java.sql.Date dateFrom;

    @Column(name = "log_date_until", nullable = false)
    private java.sql.Date dateUntil;

    @Column(name = "log_status", nullable = false)
    private Integer status;
}
