package ru.skypro.homework.service;

import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;

import java.util.Optional;

public interface UserService {

    void updatePassword(NewPassDto newPassDto);

    Optional<UserInfoDto> getInfoAboutUser(String email);

    UserUpdateDto updateInfoAboutUser(UserUpdateDto userUpdateDto,String email);
}
