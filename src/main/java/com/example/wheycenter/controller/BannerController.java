package com.example.wheycenter.controller;

import com.example.wheycenter.dto.BannerDto;
import com.example.wheycenter.response.BannerResponse;
import com.example.wheycenter.service.BannerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<List<BannerResponse>> getAllBanners() {
        List<BannerResponse> banners = bannerService.getAllBanners();
        return new ResponseEntity<>(banners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerResponse> getBannerById(@PathVariable("id") Integer bannerId) {
        BannerResponse bannerResponse = bannerService.getBannerById(bannerId);
        return bannerResponse != null ? ResponseEntity.ok(bannerResponse) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BannerResponse> createBanner(@Valid @ModelAttribute BannerDto bannerDto) throws IOException {
        // Extract the image file from the BannerDto
        MultipartFile imageFile = bannerDto.getImageFile();
        BannerResponse createdBanner = bannerService.createBanner(bannerDto, imageFile);
        return new ResponseEntity<>(createdBanner, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BannerResponse> updateBanner(@PathVariable("id") Integer bannerId,
                                                        @Valid @ModelAttribute BannerDto bannerDto) throws IOException {
        // Extract the image file from the BannerDto
        MultipartFile imageFile = bannerDto.getImageFile();
        BannerResponse updatedBanner = bannerService.updateBanner(bannerId, bannerDto, imageFile);
        return updatedBanner != null ? ResponseEntity.ok(updatedBanner) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable("id") Integer bannerId) {
        bannerService.deleteBanner(bannerId);
        return ResponseEntity.noContent().build();
    }
}
