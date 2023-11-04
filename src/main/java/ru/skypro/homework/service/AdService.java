package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
/**
 * Interface with CRUD methods for declaring
 */

public interface AdService {
    ResponseWrapperAdsDto getAllAdsDto();
    AdsDto createAds(CreateAdsDto adsDto, MultipartFile image);
    FullAdsDto getFullAdDto(Integer id);
    boolean removeAdDto(Integer id);
    AdsDto updateAdDto(Integer id, CreateAdsDto createAdsDto);
    ResponseWrapperAdsDto getAllUserAdsDto();
    void updateImageAdDto(Integer id, MultipartFile image);
    boolean checkAccess(Integer id);
}
