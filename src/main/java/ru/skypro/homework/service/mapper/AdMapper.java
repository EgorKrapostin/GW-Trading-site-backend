package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Component;

import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.entity.Ad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * A class for converting an ad-entity to dto and back
 */
@Component
public class AdMapper {

    public AdsDto mapAdToAdDto(Ad ad) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(ad.getId());
        adsDto.setAuthor(ad.getAuthor().getId());
        adsDto.setPrice(ad.getPrice());
        adsDto.setImage("/ads/" + ad.getImage().getId() + "/image");
        adsDto.setTitle(ad.getTitle());
        return adsDto;
    }


    public Ad mapAdsDtoToAd(AdsDto adsDto) {
        Ad mappedAd = new Ad();
        mappedAd.setId(adsDto.getPk());
        mappedAd.getAuthor().setId(adsDto.getAuthor());
        mappedAd.setPrice(adsDto.getPrice());
        mappedAd.getImage().setId(adsDto.getImage());
        mappedAd.setTitle(adsDto.getTitle());
        return mappedAd;
    }


    public FullAdsDto mapAdToFullAdsDTo(Ad ad) {
        FullAdsDto fullAdsDto = new FullAdsDto();
        fullAdsDto.setPk(ad.getId());
        fullAdsDto.setAuthorFirstName(ad.getAuthor().getFirstName());
        fullAdsDto.setAuthorLastName(ad.getAuthor().getLastName());
        fullAdsDto.setEmail(ad.getAuthor().getEmail());
        fullAdsDto.setPhone(ad.getAuthor().getPhone());
        fullAdsDto.setTitle(ad.getTitle());
        fullAdsDto.setDescription(ad.getDescription());
        fullAdsDto.setImage("/ads/" + ad.getImage().getId() + "/image");
        fullAdsDto.setPrice(ad.getPrice());
        return fullAdsDto;
    }


    public Ad mapCreatedAdsDtoToAd(CreateAdsDto createAdsDto) {
        Ad ad = new Ad();
        ad.setTitle(createAdsDto.getTitle());
        ad.setDescription(createAdsDto.getDescription());
        ad.setPrice(createAdsDto.getPrice());
        return ad;
    }


    public Collection<AdsDto> mapAdListToAdDtoList(Collection<Ad> adCollection) {
        List<AdsDto> dtoList = new ArrayList<>(adCollection.size());
        for (Ad ad : adCollection) {
            dtoList.add(mapAdToAdDto(ad));
        }
        return dtoList;
    }
}
