package com.example.library.repository;

import com.example.library.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findById(Long id);
    Optional<Genre> findByNameContainsIgnoreCase(String name);
}
