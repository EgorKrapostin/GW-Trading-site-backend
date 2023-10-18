package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
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
    public void updateUserImage(MultipartFile image) throws IOException {
        User user = userAuthentication.getAuthUserName();
        Path filePath = Path.of(pathImage, user + "." + (image.getName()));
        imageStream(filePath, image);

        Image avatar = findUserImage(user.getId);
        avatar.setImageName(image.getName());
        avatar.setBytes(image.getBytes());
        imageRepository.save(avatar);

        if (user.getImage() == null) {
            user.setImage(avatar);
            userRepository.save(user);
        }
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image) throws IOException {
        return new byte[0];
    }

    private void imageStream(Path filePath, MultipartFile image) throws IOException {
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
    }
    private Image findAdImage(Integer ad) {
        return adRepository.findById(ad)
                .map(Ad::getImage)
                .orElse(new Image());
    }

    private Image findUserImage(int id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .orElse(new Image());
    }
}
