package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "customer_card")
public class Customer extends User{
    @Column(name = "customer_email", nullable = false, length = 32)
    private String email;

    @Column(name = "customer_phone", nullable = true, length = 20)
    private String phone;

    @Column(name = "customer_status", nullable = false)
    private Integer status;

    @Column(name = "customer_birth_date", nullable = true)
    private LocalDate birthDate;

    @Column(name = "customer_first_name", nullable = true, length = 32)
    private String firstName;

    @Column(name = "customer_last_name", nullable = true, length = 32)
    private String lastName;

    @Column(name = "customer_patronymic", nullable = true, length = 32)
    private String patronymic;

    @Column(name = "customer_book_amount", nullable = true)
    private Integer bookAmount;
}
