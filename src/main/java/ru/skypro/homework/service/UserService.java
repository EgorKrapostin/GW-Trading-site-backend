package ru.skypro.homework.service;

import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.service.impl.PostgresUserDetailsService;

public interface UserService {

    void updatePassword(String currentPassword, String newPassword, String username);

    UserInfoDto getInfoAboutUser();

    UserInfoDto updateInfoAboutUser();
    void createUser(PostgresUserDetailsService myDatabaseUserDetails);
    boolean userExists(String username);
}
