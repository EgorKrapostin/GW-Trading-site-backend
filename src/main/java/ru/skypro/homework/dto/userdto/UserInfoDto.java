package ru.skypro.homework.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.Image;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private int id;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String image;

}
