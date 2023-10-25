package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserServiceImpl manager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;



    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encriptedPass = userDetails.getPassword();
        return encoder.matches(password, encriptedPass);
    }

    @Override
    public boolean register(Register register, Role role) {
        if (userRepository.findUserByEmail(register.getUsername()).isPresent()) {
            return false;
        }
        User userReg = userMapper.mapToUser(register);
        userReg.setRole(role);
        userReg.setPassword(encoder.encode(userReg.getPassword()));
        userRepository.save(userReg);
        return true;
    }

//    public Users getAuthUserName() {
//        Authentication authenticationUser = SecurityContextHolder.getContext().getAuthentication();
//        return userRepository.findByUsername(authenticationUser.getName());
//    }

}
