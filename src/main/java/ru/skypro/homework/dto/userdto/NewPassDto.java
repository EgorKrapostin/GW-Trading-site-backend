package ru.skypro.homework.dto.userdto;

import lombok.*;

/**
 * Wrapper class for creating a new password
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewPassDto {

    private String currentPassword;

    private String newPassword;
}
