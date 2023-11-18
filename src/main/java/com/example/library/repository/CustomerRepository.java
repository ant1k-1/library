package com.example.library.repository;

import com.example.library.model.Author;
import com.example.library.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    @Query("SELECT c FROM Customer c WHERE " +
            "c.firstName LIKE %:search% OR " +
            "c.lastName LIKE %:search% OR " +
            "c.patronymic LIKE %:search%")
    List<Customer> findBySearchFio(@Param("search") String search);
    Optional<Customer> findByLogin(String login);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAllByStatus(Integer status);
    List<Customer> findAllByBookAmountGreaterThan(Integer amountGreaterThan);
    List<Customer> findAllByBookAmountLessThan(Integer amountLessThan);
    List<Customer> findAllByBookAmountEquals(Integer amountEquals);
}
