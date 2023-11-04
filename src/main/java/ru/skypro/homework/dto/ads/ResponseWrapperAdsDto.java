package ru.skypro.homework.dto.ads;

import lombok.Data;

import java.util.Collection;
/**
 * Wrapper class for getting the list of ads
 */
@Data
public class ResponseWrapperAdsDto {
    private int count;
    private Collection<AdsDto> results;

    public ResponseWrapperAdsDto(Collection<AdsDto> results) {
        this.count = results.size();
        this.results = results;
    }
}
