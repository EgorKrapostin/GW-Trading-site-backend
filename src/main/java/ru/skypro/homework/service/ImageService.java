package ru.skypro.homework.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.File;
import java.io.IOException;
/**
 * A class with CRUD methods for the image
 */
public interface ImageService {
    Image createImage(MultipartFile image);
    Image updateImage(MultipartFile newImage, Image oldImage);
    byte[] getImage(String id);

}
