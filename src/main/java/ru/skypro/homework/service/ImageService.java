package ru.skypro.homework.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile image) throws IOException;
    String updateAdImage(String id, MultipartFile file) throws IOException;
    Image findAdImage(Integer adId);
    Image findUserImage(Integer userId);
}
