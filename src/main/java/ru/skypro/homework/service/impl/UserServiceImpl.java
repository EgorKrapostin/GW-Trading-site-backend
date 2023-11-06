package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.ImageDto;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exeption.IncorrectCurrentPasswordException;
import ru.skypro.homework.exeption.UserNotFoundException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.UserMapper;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
/**
 * A class with CRUD methods for the user
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final UserMapper userMapper;
    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;



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
    public ImageDto updateUserImage(MultipartFile file, String username) {
        User user = userRepository.findUserByEmail(username).orElseThrow(UserNotFoundException::new);
        Image image;
        if (!Objects.isNull(user.getImage())) {
            image = imageRepository.findById(user.getImage().getId()).orElse(new Image());
        } else {
            image = new Image();
            image.setId(user.getId().toString());
        }
        try {
            byte[] imageBytes = file.getBytes();
            image.setBytes(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Image returnImage = imageRepository.save(image);
        user.setImage(image);
        userRepository.save(user);
        return ImageDto.fromImage(returnImage);
    }

    @Override
    public void updateImage(MultipartFile image) {
        User user = findAuthUser().orElseThrow(UserNotFoundException::new);
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

    @Override
    public byte[] getImage(String id) {
        Image image = imageRepository.findById(id).orElseThrow();
        return image.getBytes();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " doesn't exists"));
    }

    @Override
    public void updatePassword(NewPassDto newPassword, String username) {
        User user = userRepository.findUserByEmail(username).orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new IncorrectCurrentPasswordException();
        }
    }
}
