package ru.skypro.homework.service.impl;

import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.entity.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.UserMapper;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void updatePassword(NewPassDto newPassDto) {
    }

    @Override
    public Optional<UserInfoDto> getInfoAboutUser(String email) {
        return Optional.ofNullable(
                userMapper.fromUser(userRepository.findUserByEmail(email)));

    }

    @Override
    public UserUpdateDto updateInfoAboutUser(UserUpdateDto userUpdateDto,String email) {

        Users user = userRepository.findUserByEmail(email);
        user.setFirstName(userUpdateDto.getFirtsName());
        user.setLastName(userUpdateDto.getLastName());
        user.setPhone(userUpdateDto.getPhone());

        userRepository.save(user);
        return userUpdateDto;
    }
}
