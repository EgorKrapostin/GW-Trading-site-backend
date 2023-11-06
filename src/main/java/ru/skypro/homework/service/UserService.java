package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.ImageDto;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.entity.User;

import java.util.Optional;
/**
 * A class with CRUD methods for the user
 */
public interface UserService {
    Optional<User> findAuthUser();
    UserInfoDto getInfoAboutUser();
    UserInfoDto updateInfoAboutUser(UserInfoDto userInfoDto);
    ImageDto updateUserImage(MultipartFile image, String username);
    void updateImage(MultipartFile image);
    byte [] getImage (String id);
    void updatePassword(NewPassDto newPassword, String username);
}
