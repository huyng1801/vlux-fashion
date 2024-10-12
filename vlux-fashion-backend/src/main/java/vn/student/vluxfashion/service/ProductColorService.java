package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.student.vluxfashion.dto.ProductColorDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.Product;
import vn.student.vluxfashion.model.ProductColor;
import vn.student.vluxfashion.repository.ProductColorRepository;
import vn.student.vluxfashion.repository.ProductRepository;
import vn.student.vluxfashion.response.ProductColorResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductColorService {

    @Value("${spring.application.name}")
    private String applicationName; // Retrieve application name from properties

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AwsS3Service s3Service; // AWS S3 Service

    private final String s3BucketFolder = "/product_color"; // S3 base folder path

    public List<ProductColorResponse> findAll() {
        List<ProductColor> productColors = productColorRepository.findAll();
        return productColors.stream()
                .map(this::mapToProductColorResponse)  // Map to response in the service
                .collect(Collectors.toList());
    }

    public ProductColorResponse findById(Integer id) {
        ProductColor productColor = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product color not found with id: " + id));
        return mapToProductColorResponse(productColor);  // Map to response in the service
    }

    public ProductColorResponse createProductColor(ProductColorDto productColorDto) throws IOException {
        ProductColor productColor = new ProductColor();
        productColor.setColorName(productColorDto.getColorName());

        // Fetching Product
        Product product = productRepository.findById(productColorDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productColorDto.getProductId()));

        productColor.setProduct(product);

        // Handle file upload to S3
        if (productColorDto.getImageFile() != null && !productColorDto.getImageFile().isEmpty()) {
            String imageUrl = uploadImageToS3(productColorDto.getProductId(), productColorDto.getImageFile());
            productColor.setImageUrl(imageUrl);
        }

        // Save to the repository and return the mapped response
        ProductColor savedProductColor = productColorRepository.save(productColor);
        return mapToProductColorResponse(savedProductColor); // Map and return the response
    }

    public ProductColorResponse updateProductColor(Integer id, ProductColorDto productColorDto) throws IOException {
        ProductColor existingColor = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product color not found with id: " + id));
        
        existingColor.setColorName(productColorDto.getColorName());

        // Handle file upload to S3 if a new image is provided
        if (productColorDto.getImageFile() != null && !productColorDto.getImageFile().isEmpty()) {
            String imageUrl = uploadImageToS3(productColorDto.getProductId(), productColorDto.getImageFile());
            existingColor.setImageUrl(imageUrl);
        }

        // Save to the repository and return the mapped response
        ProductColor updatedProductColor = productColorRepository.save(existingColor);
        return mapToProductColorResponse(updatedProductColor); // Map and return the response
    }

    public void deleteProductColor(Integer id) {
        ProductColor existingColor = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product color not found with id: " + id));

        // Optionally, delete the image from S3 if needed
        if (existingColor.getImageUrl() != null && !existingColor.getImageUrl().isEmpty()) {
            String key = extractS3KeyFromUrl(existingColor.getImageUrl());
            s3Service.deleteFile(key);
        }

        productColorRepository.delete(existingColor);
    }

    public List<ProductColorResponse> findByProduct_ProductId(Integer productId) {
        List<ProductColor> productColors = productColorRepository.findByProduct_ProductId(productId);
        return productColors.stream()
                .map(this::mapToProductColorResponse)
                .collect(Collectors.toList());
    }

    // Method to map ProductColor entity to ProductColorResponse DTO
    private ProductColorResponse mapToProductColorResponse(ProductColor productColor) {
        ProductColorResponse response = new ProductColorResponse();
        response.setProductColorId(productColor.getProductColorId());
        response.setColorName(productColor.getColorName());
        response.setImageUrl(productColor.getImageUrl());
        return response;
    }

    // Upload image to AWS S3 with application name and product-specific folder
    private String uploadImageToS3(Integer productId, MultipartFile file) throws IOException {
        // Generate a unique file name (UUID + original file extension)
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Construct the S3 key with the application name and product-specific folder
        String s3Key = String.format("%s%s/p%d/%s", applicationName, s3BucketFolder, productId, uniqueFileName);

        // Upload the file to S3
        s3Service.uploadFile(s3Key, file.getInputStream(), file.getSize(), file.getContentType());

        // Return the S3 URL
        return s3Service.getUrl(s3Key);
    }

    // Helper method to extract S3 key from the image URL
    private String extractS3KeyFromUrl(String url) {
        return url.substring(url.indexOf(s3BucketFolder));  // Extract the key part after the bucket/folder
    }
}
