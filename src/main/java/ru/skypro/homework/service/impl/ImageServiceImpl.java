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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;


    @Override
    public Image createImage(MultipartFile image) {
        Image newImage = new Image();
        try {
            newImage.setBytes(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageRepository.saveAndFlush(newImage);
    }


    @Override
    public Image updateImage(MultipartFile newImage, Image image) {

        try {
            image.setBytes(newImage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(image);
    }
    @Override
    public byte[] getImage(String id) {
        Image image = imageRepository.findById(id).orElseThrow();

        return image.getBytes();
    }


}
