package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.Collections;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebSecurityConfig.class})
public class AdsControllerTest {

    @InjectMocks
    AdsController adsController;
    @Mock
    private AdService adService;
    @Mock
    private CommentService commentService;
    @Mock
    private ImageService imageService;
    private final Ad testAd = new Ad();
    private final Comment testCom = new Comment();
    private final CommentDTO commentDto = new CommentDTO();
    private final CreateAdsDto createAdsDto = new CreateAdsDto();

    @BeforeEach
    public void setUp() {
        testAd.setId(1);
        testAd.setDescription("test description");
        testAd.setTitle("test title");
        testAd.setPrice(50);

        testCom.setId(2);

        commentDto.setPk(1);
        commentDto.setText("test text");

        createAdsDto.setDescription("New Description");
        createAdsDto.setTitle("New Title");
        createAdsDto.setPrice(500);
    }

    @Test
    public void getAllAdsTest() {
        List<AdsDto> adsDtoList = Collections.singletonList(new AdsDto());
        ResponseWrapperAdsDto adsDto = new ResponseWrapperAdsDto(adsDtoList);

        when(adService.getAllAdsDto()).thenReturn(adsDto);

        ResponseWrapperAdsDto responseWrapperAdsDto = adsController.getAllAds();

        verify(adService).getAllAdsDto();

        assertEquals(adsDto, responseWrapperAdsDto);
        assertNotNull(responseWrapperAdsDto);
    }

    @Test
    public void addAdTest() throws Exception {
        AdsDto adsDto = new AdsDto();
        MultipartFile image = new MockMultipartFile("test.jpg", "test.jpg",
                "image/jpeg", "test image".getBytes());

        when(adService.createAds(createAdsDto, image)).thenReturn(adsDto);

        AdsDto response = adsController.addAd(createAdsDto, image);

        verify(adService).createAds(createAdsDto, image);

        assertEquals(adsDto, response);
        assertNotNull(adsDto);
    }


    @Test
    public void getFullAdTest() {
        FullAdsDto fullAdsDto = new FullAdsDto();

        when(adService.getFullAdDto(testAd.getId())).thenReturn(fullAdsDto);

        FullAdsDto response = adsController.getAds(testAd.getId());

        verify(adService).getFullAdDto(testAd.getId());

        assertEquals(fullAdsDto, response);
        assertNotNull(fullAdsDto);
    }

    @Test
    public void removeAdTest() {
        when(adService.removeAdDto(testAd.getId())).thenReturn(true);

        boolean del = adsController.removeAd(testAd.getId());

        verify(adService).removeAdDto(testAd.getId());
        assertTrue(del);
    }

    @Test
    public void updateAdTest() {
        AdsDto adsDto = new AdsDto();
        adsDto.setTitle("New Title");
        adsDto.setPrice(500);

        when(adService.updateAdDto(testAd.getId(), createAdsDto)).thenReturn(adsDto);

        AdsDto response = adsController.updateAds(testAd.getId(), createAdsDto);

        verify(adService).updateAdDto(testAd.getId(), createAdsDto);

        assertEquals(adsDto, response);
        assertNotNull(response);
    }



    @Test
    public void getAdsMeTest() {
        List<AdsDto> adsDtoList = Collections.singletonList(new AdsDto());
        ResponseWrapperAdsDto adsDto = new ResponseWrapperAdsDto(adsDtoList);

        when(adService.getAllUserAdsDto()).thenReturn(adsDto);

        ResponseWrapperAdsDto response = adsController.getAdsForMe();

        verify(adService).getAllUserAdsDto();

        assertEquals(adsDto, response);
        assertNotNull(response);
    }

    @Test
    public void updateImageTest() throws IOException {
        MultipartFile image = new MockMultipartFile("image.jpg", new byte[]{1, 2, 3});

        doNothing().when(adService).updateImageAdDto(testAd.getId(), image);

        adsController.updateImage(testAd.getId(), image);

        verify(adService).updateImageAdDto(testAd.getId(), image);

    }

    @Test
    public void getImageTest() {
        String id = "1";
        byte[] mockImage = {1, 2, 3};

        when(imageService.getImage(id)).thenReturn(mockImage);

        byte[] response = adsController.getImage(id);

        verify(imageService).getImage(id);

        assertEquals(mockImage, response);
        assertNotNull(response);
        assertArrayEquals(mockImage, response);
    }
}