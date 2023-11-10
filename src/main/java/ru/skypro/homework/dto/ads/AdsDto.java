package ru.skypro.homework.dto.ads;

import lombok.Data;
/**
*Wrapper class for getting information about the declaration
*/

@Data
public class AdsDto {

    private int pk;

    private int author;

    private String image;

    private int price;

    private String title;
}
