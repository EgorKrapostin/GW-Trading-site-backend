package ru.skypro.homework.dto.ads;

import lombok.Data;
/**
 * Wrapper class for creating an ad
 */
@Data
public class CreateAdsDto {
    private String description;
    private int price;
    private String title;
}
