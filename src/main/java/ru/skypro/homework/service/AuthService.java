package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.User;
import ru.skypro.homework.dto.authdto.Register;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
    User getAuthUserName();
}
