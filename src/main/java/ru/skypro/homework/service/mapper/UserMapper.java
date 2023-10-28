package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.entity.User;

import java.util.Optional;

@Component
public class UserMapper {

    public UserInfoDto fromUser(User users) {

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(users.getId());
        userInfoDto.setEmail(users.getEmail());
        userInfoDto.setFirstName(users.getFirstName());
        userInfoDto.setLastName(users.getLastName());
        userInfoDto.setPhone(users.getPhone());
        Optional.ofNullable(users.getImage()).ifPresent(image -> userInfoDto.setImage(
                "/users/" + users.getImage().getId() + "/image"));
        userInfoDto.setRole(users.getRole());

        return userInfoDto;
    }

    public User toUser(UserInfoDto userInfoDto) {

        User users = new User();
        users.setEmail(userInfoDto.getEmail());
        users.setFirstName(userInfoDto.getFirstName());
        users.setLastName(userInfoDto.getLastName());
        users.setPhone(userInfoDto.getPhone());
        users.getImage().setId(userInfoDto.getImage());
        users.setRole(userInfoDto.getRole());

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
