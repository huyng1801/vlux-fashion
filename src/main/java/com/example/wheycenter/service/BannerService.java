package com.example.wheycenter.service;

import com.example.wheycenter.dto.BannerDto;
import com.example.wheycenter.model.Banner;
import com.example.wheycenter.repository.BannerRepository;
import com.example.wheycenter.response.BannerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private S3Service s3Service;

    private static final String S3_PATH_PREFIX = "wheycenter/banner/";

    public List<BannerResponse> getAllBanners() {
        return bannerRepository.findAll().stream()
                .map(this::convertToBannerResponse)
                .collect(Collectors.toList());
    }

    public BannerResponse getBannerById(Integer bannerId) {
        return bannerRepository.findById(bannerId)
                .map(this::convertToBannerResponse)
                .orElse(null);
    }

    public BannerResponse createBanner(BannerDto bannerDto, MultipartFile imageFile) throws IOException {
        String imageUrl = imageFile != null ? uploadImageToS3(imageFile) : null;
        Banner banner = new Banner();
        banner.setTitle(bannerDto.getTitle());
        banner.setImageUrl(imageUrl);
        banner.setLink(bannerDto.getLink());
        banner.setIsVisible(bannerDto.getIsVisible());
        banner.setCreatedAt(new Date());
        banner.setUpdatedAt(new Date());
        Banner savedBanner = bannerRepository.save(banner);
        return convertToBannerResponse(savedBanner);
    }

    public BannerResponse updateBanner(Integer bannerId, BannerDto bannerDto, MultipartFile imageFile) throws IOException {
        Optional<Banner> optionalBanner = bannerRepository.findById(bannerId);
        if (optionalBanner.isEmpty()) {
            return null;
        }
        Banner banner = optionalBanner.get();
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = uploadImageToS3(imageFile);
            banner.setImageUrl(imageUrl);
        }
        banner.setTitle(bannerDto.getTitle());
        banner.setLink(bannerDto.getLink());
        banner.setIsVisible(bannerDto.getIsVisible());
        banner.setUpdatedAt(new Date());
        Banner updatedBanner = bannerRepository.save(banner);
        return convertToBannerResponse(updatedBanner);
    }

    public void deleteBanner(Integer bannerId) {
        Optional<Banner> optionalBanner = bannerRepository.findById(bannerId);
        if (optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            String imageUrl = banner.getImageUrl();
            // Extract file name from imageUrl and delete from S3
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String keyName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
                s3Service.deleteFile(S3_PATH_PREFIX + keyName);
            }
            bannerRepository.deleteById(bannerId);
        }
    }

    private String uploadImageToS3(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String keyName = S3_PATH_PREFIX + UUID.randomUUID() + fileExtension;

        try (InputStream inputStream = imageFile.getInputStream()) {
            s3Service.uploadFile(keyName, inputStream, imageFile.getSize(), imageFile.getContentType());
        }
        return s3Service.getUrl(keyName);
    }

    private BannerResponse convertToBannerResponse(Banner banner) {
        return new BannerResponse(
                banner.getBannerId(),
                banner.getTitle(),
                banner.getImageUrl(),
                banner.getLink(),
                banner.getIsVisible(),
                banner.getCreatedAt(),
                banner.getUpdatedAt()
        );
    }
}
