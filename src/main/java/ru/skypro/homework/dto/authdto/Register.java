package ru.skypro.homework.dto.authdto;

import lombok.Data;
/**
 * Wrapper class for registration
 */
@Data
public class Register {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
