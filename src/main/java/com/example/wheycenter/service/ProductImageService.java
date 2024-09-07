package com.example.wheycenter.service;

import com.example.wheycenter.dto.ProductImageDto;
import com.example.wheycenter.model.ProductImage;
import com.example.wheycenter.repository.ProductImageRepository;
import com.example.wheycenter.repository.ProductRepository;
import com.example.wheycenter.response.ProductImageResponse;
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
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private S3Service s3Service;  // Assuming you have an S3Service for handling file uploads

    private static final String S3_PATH_PREFIX = "wheycenter/product/";

    public ProductImageResponse addImage(ProductImageDto productImageDto) throws IOException {
        MultipartFile imageFile = productImageDto.getImageFile();
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }

        String imageUrl = uploadImageToS3(imageFile);

        ProductImage productImage = new ProductImage();
        productImage.setImageUrl(imageUrl);
        productImage.setIsMain(productImageDto.getIsMain());
        productImage.setProduct(productRepository.getById(productImageDto.getProductId()));

        ProductImage savedProductImage = productImageRepository.save(productImage);
        return convertToProductImageResponse(savedProductImage);
    }

    public void updateImage(Integer imageId, ProductImageDto productImageDto) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(imageId);
        if (optionalProductImage.isPresent()) {
            ProductImage productImage = optionalProductImage.get();
            productImage.setIsMain(productImageDto.getIsMain());
            productImageRepository.save(productImage);
        }
    }

    public void deleteImage(Integer imageId) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(imageId);
        if (optionalProductImage.isPresent()) {
            ProductImage productImage = optionalProductImage.get();
            String imageUrl = productImage.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String keyName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
                s3Service.deleteFile(S3_PATH_PREFIX + keyName);
            }
            productImageRepository.deleteById(imageId);
        }
    }

    public List<ProductImageResponse> getImagesByProductId(String productId) {
        return productImageRepository.findByProduct_ProductId(productId).stream()
                .map(this::convertToProductImageResponse)
                .collect(Collectors.toList());
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

    private ProductImageResponse convertToProductImageResponse(ProductImage productImage) {
        return new ProductImageResponse(
                productImage.getProductImageId(),
                productImage.getImageUrl(),
                productImage.getIsMain()
        );
    }
}
