package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;
import ru.skypro.homework.dto.userdto.UserUpdateDto;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {


    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public void updatePassword(
            @RequestBody NewPassDto newPassDto) {

    }

    @GetMapping("/me")
    public Optional<UserInfoDto> getInfoAboutUser(
            @RequestParam("email") String email) {
        return userService.getInfoAboutUser(email);
    }

    @PatchMapping("/me")
    public UserUpdateDto updateInfoAboutUser(
            @RequestBody UserUpdateDto userUpdateDto,
            @RequestParam("email") String email) {
        return userService.updateInfoAboutUser(userUpdateDto,email);
    }

    @PatchMapping("/me/image")
  public byte[] updateUserImage(
            @RequestPart MultipartFile image) {
        return null;
    }
}
