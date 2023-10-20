package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.Users;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageServiceImpl implements ImageService {
    private final AdRepository adRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final AuthServiceImpl userAuthentication;

    @Value("${path.image}")
    private String pathImage;

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        Users user = userAuthentication.getAuthUserName();

        Path filePath = Path.of(pathImage + user.getUsername(),
                "user_image.jpg");

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return "/" + filePath.getParent().toString();
    }


    @Override
    public String updateAdImage(String id, MultipartFile image) throws IOException {
        Users user = userAuthentication.getAuthUserName();

        Path filePath = Path.of(pathImage + user.getUsername()+ "/ad",
                image.getOriginalFilename());

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return "/" + filePath.toString().replace("\\", "/");
    }
    @Override
    public Image findAdImage(Integer adId) {
        return adRepository.findById(adId)
                .map(Ad::getImage)
                .orElse(new Image());
    }
    @Override
    public Image findUserImage(Integer userId) {
        return userRepository.findById(userId)
                .map(Users::getImage)
                .orElse(new Image());
    }


}
