package ru.skypro.homework.dto.authdto;

import lombok.Data;
/**
 * Wrapper class for authorization
 */
@Data
public class Login {
    private String email;
    private String password;
}
