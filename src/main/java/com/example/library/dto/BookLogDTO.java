package com.example.library.dto;

public record BookLogDTO(
        Long id,
        Long cardId,
        Long bookId,
        Long libstaffId,
        java.sql.Date dateFrom,
        java.sql.Date dateUntil,
        Integer status,
        String title
) {
}
