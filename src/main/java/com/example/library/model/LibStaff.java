package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "libstaff")
public class LibStaff{
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "libstaff_phone", nullable = true, length = 20)
    private String phone;

    @Column(name = "libstaff_first_name", nullable = true, length = 32)
    private String firstName;

    @Column(name = "libstaff_last_name", nullable = true, length = 32)
    private String lastName;

    @Column(name = "libstaff_patronymic", nullable = true, length = 32)
    private String patronymic;
}
