package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;

import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final ImageService imageService;
    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/set_password")
    public void updatePassword(
            @RequestBody NewPassDto newPassDto) {

    }

    @GetMapping("/me")
    public UserInfoDto getUser() {
        return userService.getInfoAboutUser();
    }

    @PatchMapping("/me")
    public UserInfoDto updateInfoAboutUser(
            @RequestBody UserInfoDto userInfoDto) {
        return userService.updateInfoAboutUser(userInfoDto);
    }

    @PatchMapping("/me/image")
  public byte[] updateUserImage(
            @RequestPart MultipartFile image) {
        return null;
    }
}
