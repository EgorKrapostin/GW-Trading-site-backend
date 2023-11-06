package ru.skypro.homework.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Image;
/**
 * The class-wrapper for getting image
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private String id;

    private byte [] image;

    public static ImageDto fromImage(Image image) {
        return new ImageDto(image.getId(), image.getBytes());
    }
}
