package ru.skypro.homework.dto.ads;

import lombok.Data;
/**
 * Wrapper class for getting the full declaration
 */
@Data
public class FullAdsDto {
    private int pk;

    private String authorFirstName;

    private String authorLastName;

    private String description;

    private String email;

    private String phone;

    private String image;

    private int price;

    private String title;
}
