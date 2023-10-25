package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.entity.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public Users getAuthUserName() {
        Authentication authenticationUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authenticationUser.getName());
    }

    @Override
    @Transactional
    public void updatePassword(String currentPassword, String newPassword, String username) {
        Users user = userRepository.findByUsername(username);
        if (encoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new BadCredentialsException("Invalid current password!");
        }
    }

    @Override
    public UserInfoDto getInfoAboutUser() {
        return new UserInfoDto();

    }

    @Override
    public UserInfoDto updateInfoAboutUser() {
        return new UserInfoDto();
    }

    @Override
    public void createUser(PostgresUserDetailsService myDatabaseUserDetails) {

    }

    @Override
    @Transactional
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
