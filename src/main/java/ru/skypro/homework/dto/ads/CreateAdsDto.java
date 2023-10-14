package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data
public class CreateAdsDto {
    private String description;
    private int price;
    private String title;
}
