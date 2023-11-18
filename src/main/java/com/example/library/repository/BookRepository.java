package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
    List<Book> findAllByTitleContainsIgnoreCase(String title);
    List<Book> findAllByYear(Integer year);
    List<Book> findAllByAmount(Integer amount);
    List<Book> findAllByAuthorId(Long id);
    List<Book> findAllByPublishmentId(Long id);
}
