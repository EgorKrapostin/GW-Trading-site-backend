package ru.skypro.homework.service;

import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;

public interface UserService {

    void updatePassword(NewPassDto newPassDto);

    UserInfoDto getInfoAboutUser();

    UserInfoDto updateInfoAboutUser();

}
