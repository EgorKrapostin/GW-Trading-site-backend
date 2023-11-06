package ru.skypro.homework.dto.userdto;

import lombok.Data;
/**
 * Wrapper class for creating a new password
 */
@Data
public class NewPassDto {

    private String currentPassword;

    private String newPassword;
}
