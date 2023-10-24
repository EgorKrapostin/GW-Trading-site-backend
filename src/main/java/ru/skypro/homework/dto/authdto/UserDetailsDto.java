package ru.skypro.homework.dto.authdto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class UserDetailsDto {
    private final String username;
    private final String password;
    private final Integer userId;
    private final Role role;

}
