package com.example.library.repository;

import com.example.library.model.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookLogRepository extends JpaRepository<BookLog, Long> {
    Optional<BookLog> findByCardIdAndBookIdAndLibstaffId(Long cardId, Long bookId, Long libstaffid);
    List<BookLog> findByStatus(Integer status);
    List<BookLog> findAllByCardId(Long id);
}
