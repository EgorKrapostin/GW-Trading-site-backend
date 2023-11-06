package ru.skypro.homework.dto.userdto;

import lombok.Data;
/**
 * Wrapper class for changing user data
 */
@Data
public class UserUpdateDto {

    private String firtsName;

    private String lastName;

    private String phone;
}
