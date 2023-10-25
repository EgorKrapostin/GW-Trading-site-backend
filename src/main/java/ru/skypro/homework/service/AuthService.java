package ru.skypro.homework.service;

import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.entity.Users;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
}
