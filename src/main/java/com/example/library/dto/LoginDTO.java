package com.example.library.dto;

import com.example.library.enums.Role;

public record LoginDTO(Role role, String login, String password) {
}
