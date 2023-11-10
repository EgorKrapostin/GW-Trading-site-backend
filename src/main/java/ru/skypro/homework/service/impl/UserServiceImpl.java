package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.UserMapper;

import java.util.Optional;
/**
 * A class with CRUD methods for the user
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    /**
     * Method for checking the user for authorization
     */
    @Override
    public Optional<User> findAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findUserByEmail(currentPrincipalName);
    }
    /**
     * Method for changing the user's avatar
     */
    @Override
    public void updateUserImage(MultipartFile image) {
        User user = findAuthUser().orElseThrow();
        Image oldImage = user.getImage();
        if (oldImage == null) {
            Image newImage = imageService.createImage(image);
            user.setImage(newImage);
        } else {
            Image updatedImage = imageService.updateImage(image, oldImage);
            user.setImage(updatedImage);
        }
        userRepository.save(user);
    }
    /**
     * Method for getting user information
     */
    @Override
    public UserInfoDto getInfoAboutUser() {
        Optional<User> currentUser = findAuthUser();
        UserInfoDto currentUserDto = new UserInfoDto();
        if (currentUser.isPresent()) {
            currentUserDto = userMapper.fromUser(currentUser.get());
        }
        return currentUserDto;
    }
    /**
     * Method for changing user information
     */
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
    /**
     * Method for checking the user for registration
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " doesn't exists"));
    }
}
