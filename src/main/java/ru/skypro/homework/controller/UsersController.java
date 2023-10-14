package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userdto.NewPassDto;
import ru.skypro.homework.dto.userdto.UserInfoDto;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {


    @PostMapping("/set_password")
    public void updatePassword(
            @RequestBody NewPassDto newPassDto) {

    }

    @GetMapping("/me")
    public UserInfoDto getInfoAboutUser() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(1);
        userInfoDto.setFirstName("222");
        userInfoDto.setLastName("Коп");
        userInfoDto.setPhone("12345");
        return userInfoDto;
    }

    @PatchMapping("/me")
    public UserInfoDto updateInfoAboutUser(
            @RequestBody UserInfoDto userInfoDto) {
        return userInfoDto; //изменить на DTO
    }

    @PatchMapping("/me/image")
  public byte[] updateUserImage(
            @RequestPart MultipartFile image) {
        return null;
    }
}
