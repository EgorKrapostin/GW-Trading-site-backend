package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.mapper.AdMapper;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdMapper adMapper;

    private final AdRepository adRepository;
    @Override
    public ResponseWrapperAdsDto getAllAdsDto() {
        Collection<AdsDto> adsAll = adMapper.mapAdListToAdDtoList(adRepository.findAll()) ;
        return new ResponseWrapperAdsDto(adsAll);
    }

    @Override
    public AdsDto createAds(CreateAdsDto adsDto, MultipartFile image) {
        Ad newAd = adMapper.mapCreatedAdsDtoToAd(adsDto);
        return adMapper.mapAdToAdDto(newAd);
    }

    @Override
    public FullAdsDto getFullAdDto(Integer id) {
        return null;
    }

    @Override
    public boolean removeAdDto(Integer id) {
        if(checkAccess(id)){
            adRepository.deleteById(id);
            return true;
        }
        return false;
     //  throw new NotFornidden(); дописать ошибку
    }

    @Override
    public AdsDto updateAdDto(Integer id, CreateAdsDto createAdsDto) {
        Ad ad = adRepository.findById(id).orElseThrow();
        if(checkAccess(id)){
            ad.setTitle(createAdsDto.getTitle());
            ad.setPrice(createAdsDto.getPrice());
            ad.setDescription(createAdsDto.getDescription());
            return adMapper.mapAdToAdDto(adRepository.save(ad));
        }
        //тут нужен эксэпшн
        return null;
    }

    @Override
    public ResponseWrapperAdsDto getAllUserAdsDto() {
        return null;
    }

    @Override
    public void updateImageAdDto(Integer id, MultipartFile image) {

    }

    @Override
    public boolean checkAccess(Integer id) {
      Role role = Role.ADMIN;
      Ad ad = adRepository.findById(id).orElseThrow();





        return false;
    }
}
