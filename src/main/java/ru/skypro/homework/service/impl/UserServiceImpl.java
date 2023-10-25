package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.UserMapper;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public Optional<User> findAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findUserByEmail(currentPrincipalName);
    }

    @Override
    public UserInfoDto getInfoAboutUser() {
        Optional<User> currentUser = findAuthUser();
        UserInfoDto currentUserDto = new UserInfoDto();
        if (currentUser.isPresent()) {
            currentUserDto = userMapper.fromUser(currentUser.get());
        }
        return currentUserDto;
    }

    @Override
    public UserInfoDto updateInfoAboutUser(UserInfoDto userInfoDto) {
        Optional<User> currentUser = findAuthUser();
        User user = new User();
        if(currentUser.isPresent()){
            user = currentUser.get();
            user.setFirstName(userInfoDto.getFirstName());
            user.setLastName(userInfoDto.getLastName());
            user.setPhone(userInfoDto.getPhone());
            userRepository.save(user);
        }

        return userMapper.fromUser(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " doesn't exists"));
    }
}
