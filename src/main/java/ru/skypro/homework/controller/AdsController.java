package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class AdsController {
    private final AdService adService;
    private final ImageService imageService;

    @GetMapping()
    public ResponseWrapperAdsDto getAllAds(){
       return adService.getAllAdsDto();
    }

    @PostMapping()
    public ResponseEntity<AdsDto> addAds(@RequestPart CreateAdsDto ads,
                                         @RequestPart MultipartFile image){
        return ResponseEntity.ok(adService.createAds(ads, image));
    }

    @GetMapping("/{id}")
    public FullAdsDto getAds(@PathVariable Integer id){
        return adService.getFullAdDto(id);
    }

    @DeleteMapping("/{id}")
    public boolean removeAd(@PathVariable Integer id) {
        return adService.removeAdDto(id);
    }

    @PatchMapping("/{id}")
    public AdsDto updateAds(@PathVariable Integer id,
                            @RequestPart CreateAdsDto adsDto){
        return adService.updateAdDto(id, adsDto);
    }

    @GetMapping("/me")
    public ResponseWrapperAdsDto getAdsForMe(){
        return adService.getAllUserAdsDto();
    }

    @PatchMapping("/{id}/image")
    public void updateImage(@PathVariable Integer id,
                            @RequestPart MultipartFile image){
        adService.updateImageAdDto(id, image);
    }
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return imageService.getImage(id);
    }
}

