package com.example.library.dto;

import java.time.LocalDate;

public record UserDTO(String email,
                      String login,
                      String phone,
                      Integer status,
                      LocalDate birthDate,
                      String firstName,
                      String lastName,
                      String patronymic,
                      Integer bookAmount) {
}
