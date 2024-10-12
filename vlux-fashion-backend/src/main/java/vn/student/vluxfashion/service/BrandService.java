package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.student.vluxfashion.dto.BrandDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.Brand;
import vn.student.vluxfashion.repository.BrandRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private AwsS3Service s3Service;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String BRAND_PATH = "/brand/";

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Brand with ID " + id + " not found"));
    }

    public Brand createBrand(BrandDto brandDto) throws IOException {
        Brand brand = new Brand();
        brand.setBrandName(brandDto.getBrandName());

        // Handle image file upload
        if (brandDto.getImageFile() != null && !brandDto.getImageFile().isEmpty()) {
            String imageUrl = uploadImageToS3(brandDto.getImageFile(), brandDto.getBrandName());
            brand.setImageUrl(imageUrl);
        }

        brand.setCreatedAt(new Date());
        brand.setUpdatedAt(new Date());
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Integer id, BrandDto brandDto) throws IOException {
        Brand brand = brandRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Brand with ID " + id + " not found"));

        brand.setBrandName(brandDto.getBrandName());

        // Handle image file upload
        if (brandDto.getImageFile() != null && !brandDto.getImageFile().isEmpty()) {
            String imageUrl = uploadImageToS3(brandDto.getImageFile(), brandDto.getBrandName());
            brand.setImageUrl(imageUrl);
        }

        brand.setUpdatedAt(new Date());
        return brandRepository.save(brand);
    }

    public void deleteBrand(Integer id) {
        Brand brand = brandRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Brand with ID " + id + " not found"));

        String imageUrl = brand.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String keyName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            s3Service.deleteFile(applicationName + BRAND_PATH + keyName);
        }

        brandRepository.delete(brand);
    }

    private String uploadImageToS3(MultipartFile imageFile, String brandName) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        int width, height;

        // Reading image dimensions
        try (InputStream inputStream = imageFile.getInputStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            if (bufferedImage == null) {
                throw new IOException("Invalid image file");
            }
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
        }

        String formattedName = brandName.toLowerCase().replaceAll("[^a-z0-9]+", "-");
        String keyName = String.format("%s%s-%dx%d%s", applicationName + BRAND_PATH, formattedName, width, height, fileExtension);

        // Upload the file to S3
        try (InputStream inputStream = imageFile.getInputStream()) {
            s3Service.uploadFile(keyName, inputStream, imageFile.getSize(), imageFile.getContentType());
        }

        return s3Service.getUrl(keyName);
    }
}
