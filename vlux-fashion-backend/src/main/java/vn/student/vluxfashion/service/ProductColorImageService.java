package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.student.vluxfashion.dto.ProductColorImageDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.ProductColor;
import vn.student.vluxfashion.model.ProductColorImage;
import vn.student.vluxfashion.repository.ProductColorImageRepository;
import vn.student.vluxfashion.repository.ProductColorRepository;
import vn.student.vluxfashion.response.ProductColorImageResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductColorImageService {

    @Value("${spring.application.name}")
    private String applicationName; // Retrieve application name from properties

    @Autowired
    private ProductColorImageRepository productColorImageRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private AwsS3Service awsS3Service; // Inject the AwsS3Service

    // Base folder path in S3 for product color images
    private final String s3BucketFolder = "product_color"; // No leading slash

    // Find all images by ProductColor ID and map to response DTO
    public List<ProductColorImageResponse> findByProductColorId(Integer productColorId) {
        return productColorImageRepository.findAll().stream()
                .filter(image -> image.getProductColor().getProductColorId().equals(productColorId))
                .map(image -> new ProductColorImageResponse(image.getProductColorImageId(), image.getImageUrl(), image.getProductColor().getProductColorId()))
                .collect(Collectors.toList());
    }

    // Create new ProductColorImages and return response DTOs
    public List<ProductColorImageResponse> createProductColorImages(ProductColorImageDto productColorImageDto) throws IOException {
        ProductColor productColor = productColorRepository.findById(productColorImageDto.getProductColorId())
                .orElseThrow(() -> new ResourceNotFoundException("Product color not found with ID: " + productColorImageDto.getProductColorId()));

        // Store each image and map to response DTO
        return productColorImageDto.getImageFiles().stream()
                .map(imageFile -> {
                    try {
                        // Generate a unique filename using UUID to avoid overwriting
                        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

                        // Upload to S3 and get the file URL, storing in the product color folder
                        String imageUrl = awsS3Service.uploadFile(getS3KeyForProductColor(productColor.getProductColorId(), uniqueFileName),
                                imageFile.getInputStream(),
                                imageFile.getSize(),
                                imageFile.getContentType());

                        ProductColorImage productColorImage = new ProductColorImage();
                        productColorImage.setImageUrl(imageUrl);
                        productColorImage.setProductColor(productColor);
                        ProductColorImage savedImage = productColorImageRepository.save(productColorImage);

                        return new ProductColorImageResponse(savedImage.getProductColorImageId(), savedImage.getImageUrl(), productColor.getProductColorId());
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save image: " + imageFile.getOriginalFilename(), e);
                    }
                }).collect(Collectors.toList());
    }

    // Update existing ProductColorImage and return the updated response DTO
    public ProductColorImageResponse updateProductColorImage(Integer imageId, ProductColorImageDto productColorImageDto) throws IOException {
        ProductColorImage existingImage = productColorImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with ID: " + imageId));

        // Optionally handle file replacement
        if (productColorImageDto.getImageFiles() != null && !productColorImageDto.getImageFiles().isEmpty()) {
            MultipartFile imageFile = productColorImageDto.getImageFiles().get(0); // Assuming we want to update with the first image

            // Generate a unique filename to avoid overwriting
            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            String imageUrl = awsS3Service.uploadFile(getS3KeyForProductColor(existingImage.getProductColor().getProductColorId(), uniqueFileName),
                    imageFile.getInputStream(),
                    imageFile.getSize(),
                    imageFile.getContentType());
            existingImage.setImageUrl(imageUrl);
        }

        ProductColorImage updatedImage = productColorImageRepository.save(existingImage);
        return new ProductColorImageResponse(updatedImage.getProductColorImageId(), updatedImage.getImageUrl(), existingImage.getProductColor().getProductColorId());
    }

    // Delete ProductColorImage
    public void deleteProductColorImage(Integer imageId) {
        ProductColorImage existingImage = productColorImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with ID: " + imageId));

        // Delete the image file from S3
        awsS3Service.deleteFile(existingImage.getImageUrl());

        productColorImageRepository.delete(existingImage);
    }

    // Helper method to construct S3 key for product color images
    private String getS3KeyForProductColor(Integer productColorId, String uniqueFileName) {
        return String.format("%s/%s/%d/%s", applicationName, s3BucketFolder, productColorId, uniqueFileName);
    }

    // Additional CRUD methods can be implemented...
}
