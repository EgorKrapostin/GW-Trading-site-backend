package ru.skypro.homework.dto.authdto;

import org.springframework.security.core.GrantedAuthority;
/**
 * A class for selecting a user role
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
