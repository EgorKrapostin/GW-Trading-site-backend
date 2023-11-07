package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.*;
import java.util.UUID;
/**
 * A class with CRUD methods for the image
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    /**
     * Method for creating and saving an image
     */
    @Override
    public Image createImage(MultipartFile image) {
        Image newImage = new Image();
        try {
            byte[] bytes = image.getBytes();
            newImage.setBytes(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newImage.setId(UUID.randomUUID().toString());
        return imageRepository.saveAndFlush(newImage);
    }
    /**
     * Method for changing and saving an image
     */
    @Override
    public Image updateImage(MultipartFile newImage, Image image) {

        try {
            byte[] bytes = newImage.getBytes();
            image.setBytes(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(image);
    }
    /**
     * Method for getting an image in bytes
     */
    @Override
    public byte[] getImage(String id) {
        Image image = imageRepository.findById(id).orElseThrow();

        return image.getBytes();
    }


}
