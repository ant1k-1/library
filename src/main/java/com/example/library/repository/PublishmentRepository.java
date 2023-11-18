package com.example.library.repository;

import com.example.library.model.Publishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishmentRepository extends JpaRepository<Publishment, Long> {
    Optional<Publishment> findById(Long id);
    Optional<Publishment> findByNameIgnoreCase(String name);
}
