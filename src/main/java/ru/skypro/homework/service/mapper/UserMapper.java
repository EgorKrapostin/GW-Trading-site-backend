package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.entity.User;

@Component
public class UserMapper {

    public UserInfoDto fromUser(User users) {

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(users.getId());
        userInfoDto.setEmail(users.getEmail());
        userInfoDto.setFirstName(users.getFirstName());
        userInfoDto.setLastName(users.getLastName());
        userInfoDto.setPhone(users.getPhone());
        userInfoDto.setImage(users.getImage());

        return userInfoDto;
    }

    public User toUser(UserInfoDto userInfoDto) {

        User users = new User();
        users.setEmail(userInfoDto.getEmail());
        users.setFirstName(userInfoDto.getFirstName());
        users.setLastName(userInfoDto.getLastName());
        users.setPhone(userInfoDto.getPhone());
        users.setImage(userInfoDto.getImage());

        return users;
    }

    public User mapToUser(Register register) {
        User mappedUser = new User();
        mappedUser.setUsername(register.getUsername());
        mappedUser.setPassword(register.getPassword());
        mappedUser.setFirstName(register.getFirstName());
        mappedUser.setLastName(register.getLastName());
        mappedUser.setPhone(register.getPhone());
        mappedUser.setRole(register.getRole());
        mappedUser.setEmail(register.getUsername());
        return mappedUser;
    }
}
