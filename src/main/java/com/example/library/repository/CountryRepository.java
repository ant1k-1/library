package com.example.library.repository;

import com.example.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findById(Long id);
    Optional<Country> findByNameContainsIgnoreCase(String name);
}
