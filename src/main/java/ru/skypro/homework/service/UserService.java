package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.entity.User;

import java.util.Optional;
/**
 * A class with CRUD methods for the user
 */
public interface UserService {

    UserInfoDto getInfoAboutUser();

    UserInfoDto updateInfoAboutUser(UserInfoDto userInfoDto);

    Optional<User> findAuthUser();

    void updateUserImage(MultipartFile image);
}
