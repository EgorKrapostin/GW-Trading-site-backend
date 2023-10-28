package ru.skypro.homework.service;

import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.entity.User;

import java.util.Optional;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register, Role role);

    void updatePassword(NewPassDto newPassDto);

}
