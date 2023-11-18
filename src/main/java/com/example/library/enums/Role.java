package com.example.library.enums;

public enum Role {
    ROLE_CUSTOMER, ROLE_STAFF, ROLE_ADMIN;
    public String toString() {
        return switch (this) {
            case ROLE_ADMIN -> "Администратор";
            case ROLE_CUSTOMER -> "Читатель";
            case ROLE_STAFF -> "Сотрудник";
        };
    }
}
