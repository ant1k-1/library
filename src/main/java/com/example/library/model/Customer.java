package com.example.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
@Table(name = "customer_card")
public class Customer{
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "customer_email", nullable = true, length = 32)
    private String email;

    @Column(name = "customer_phone", nullable = true, length = 20)
    private String phone;

    @Column(name = "customer_status", nullable = true)
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
