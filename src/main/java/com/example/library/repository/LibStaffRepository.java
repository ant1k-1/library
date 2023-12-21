package com.example.library.repository;

import com.example.library.model.Customer;
import com.example.library.model.LibStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibStaffRepository extends JpaRepository<LibStaff, Long> {
    Optional<LibStaff> findById(Long id);
//    Optional<LibStaff> findByLogin(String login);
    Optional<LibStaff> findByPhoneContainsIgnoreCase(String phone);
    @Query("SELECT l FROM LibStaff l WHERE " +
            "l.firstName LIKE %:search% OR " +
            "l.lastName LIKE %:search% OR " +
            "l.patronymic LIKE %:search%")
    List<Customer> findBySearchFio(@Param("search") String search);
    Optional<LibStaff> findByUserId(Long userId);
}
