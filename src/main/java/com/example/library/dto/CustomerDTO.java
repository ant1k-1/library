package com.example.library.dto;

import java.time.LocalDate;

public record CustomerDTO(String email,
                          String login,
                          String password,
                          String phone,
                          Integer status,
                          LocalDate birthDate,
                          String firstName,
                          String lastName,
                          String patronymic,
                          Integer bookAmount) {
}
