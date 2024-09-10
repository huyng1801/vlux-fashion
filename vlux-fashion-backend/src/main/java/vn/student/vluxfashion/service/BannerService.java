package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.student.vluxfashion.dto.BannerDto;
import vn.student.vluxfashion.exception.FileUploadException;
import vn.student.vluxfashion.model.Banner;
import vn.student.vluxfashion.repository.BannerRepository;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

@Service
public class BannerService {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String BANNER_PATH = "/banner/";

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private AwsS3Service s3Service;

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll().stream().collect(Collectors.toList());
    }

    public Banner getBannerById(Integer bannerId) {
        return bannerRepository.findById(bannerId)
            .orElse(null);
    }

    public Banner createBanner(BannerDto bannerDto) {
        String imageUrl = null;

        if (bannerDto.getImageFile() != null) {
            try {
                imageUrl = uploadImageToS3(bannerDto.getImageFile(), bannerDto.getTitle());
            } catch (IOException e) {
                throw new FileUploadException("Error uploading image to S3", e);
            }
        }

        Banner banner = new Banner();
        banner.setTitle(bannerDto.getTitle());
        banner.setImageUrl(imageUrl);
        banner.setLink(bannerDto.getLink());
        banner.setIsVisible(bannerDto.getIsVisible());
        banner.setCreatedAt(new Date());
        banner.setUpdatedAt(new Date());

        return bannerRepository.save(banner);
    }

    public Banner updateBanner(Integer bannerId, BannerDto bannerDto) {
        Banner banner = bannerRepository.findById(bannerId)
            .orElse(null);

        if (bannerDto.getImageFile() != null && !bannerDto.getImageFile().isEmpty()) {
            try {
                String imageUrl = uploadImageToS3(bannerDto.getImageFile(), bannerDto.getTitle());
                banner.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new FileUploadException("Error uploading image to S3", e);
            }
        }

        banner.setTitle(bannerDto.getTitle());
        banner.setLink(bannerDto.getLink());
        banner.setIsVisible(bannerDto.getIsVisible());
        banner.setUpdatedAt(new Date());

        return bannerRepository.save(banner);
    }

    public void deleteBanner(Integer bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
        .orElse(null);

        String imageUrl = banner.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String keyName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            s3Service.deleteFile(applicationName + BANNER_PATH + keyName);
        }

        bannerRepository.deleteById(bannerId);
    }

    private String uploadImageToS3(MultipartFile imageFile, String title) throws IOException {
        try {
            return uploadImage(imageFile, title);
        } catch (IOException e) {
            throw new FileUploadException("Error uploading image to S3", e);
        }
    }

    private String uploadImage(MultipartFile imageFile, String title) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        int width, height;

        try (InputStream inputStream = imageFile.getInputStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            if (bufferedImage == null) {
                throw new IOException("Invalid image file");
            }
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
        }

        String formattedTitle = title.toLowerCase().replaceAll("[^a-z0-9]+", "-");
        String keyName = String.format("%s%s-%dx%d%s", applicationName + BANNER_PATH, formattedTitle, width, height, fileExtension);

        try (InputStream inputStream = imageFile.getInputStream()) {
            s3Service.uploadFile(keyName, inputStream, imageFile.getSize(), imageFile.getContentType());
        }

        return s3Service.getUrl(keyName);
    }
}
