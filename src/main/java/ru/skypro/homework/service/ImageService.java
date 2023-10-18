package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void updateUserImage(MultipartFile image) throws IOException;
    byte[] updateAdImage(Integer id, MultipartFile image) throws IOException;
}
