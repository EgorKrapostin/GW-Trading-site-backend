package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updatePassword(NewPassDto newPassDto) {
    }

    @Override
    public UserInfoDto getInfoAboutUser() {
        return new UserInfoDto();

    }

    @Override
    public UserInfoDto updateInfoAboutUser() {
        return new UserInfoDto();
    }
}
