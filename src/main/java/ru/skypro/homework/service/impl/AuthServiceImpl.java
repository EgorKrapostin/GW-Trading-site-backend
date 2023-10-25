package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.entity.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;


    @Override
    public boolean login(String userName, String password) {
        if (!userService.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        Users user = userMapper.toRegister(register);
        PostgresUserDetailsService myDatabaseUserDetails = new PostgresUserDetailsService((UserRepository) user);
        if (! userService.userExists(register.getUsername())) {
            userService.createUser(myDatabaseUserDetails);
            return true;
        } else {
            return false;
        }
    }
}
