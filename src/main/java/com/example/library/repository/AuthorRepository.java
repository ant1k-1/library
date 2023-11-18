package com.example.library.repository;

import com.example.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);

    // Поиск по имени, фамилии, отчеству и псевдониму автора
    @Query("SELECT a FROM Author a WHERE " +
            "a.firstName LIKE %:search% OR " +
            "a.lastName LIKE %:search% OR " +
            "a.patronymic LIKE %:search% OR " +
            "a.pseudonym LIKE %:search%")
    List<Author> findBySearchTerm(@Param("search") String search);

    List<Author> findByCountryId(Long id);
}
