package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.entity.Users;
@Component
public class UserMapper {

    public UserInfoDto fromUser(Users users) {

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(users.getId());
        userInfoDto.setEmail(users.getEmail());
        userInfoDto.setFirstName(users.getFirstName());
        userInfoDto.setLastName(users.getLastName());
        userInfoDto.setPhone(users.getPhone());
        userInfoDto.setRole(users.getRole());
        userInfoDto.setImage(users.getImage());

        return userInfoDto;
    }

    public Users toUser(UserInfoDto userInfoDto) {

        Users users = new Users();
        users.setEmail(userInfoDto.getEmail());
        users.setFirstName(userInfoDto.getFirstName());
        users.setLastName(userInfoDto.getLastName());
        users.setPhone(userInfoDto.getPhone());
        users.setRole(userInfoDto.getRole());
        users.setImage(userInfoDto.getImage());

        return users;
    }
    public Users toRegister(Register register) {

        Users users = new Users();
        users.setUsername(register.getUsername());
        users.setPassword(register.getPassword());
        users.setFirstName(register.getFirstName());
        users.setLastName(register.getLastName());
        users.setPhone(register.getPhone());
        users.setRole(register.getRole());
        return users;
    }
}
