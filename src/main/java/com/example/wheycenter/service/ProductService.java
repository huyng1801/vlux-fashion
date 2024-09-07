package com.example.wheycenter.service;

import com.example.wheycenter.dto.ProductDto;
import com.example.wheycenter.response.ProductResponse;
import com.example.wheycenter.model.Manufacturer;
import com.example.wheycenter.model.Origin;
import com.example.wheycenter.model.Product; // Assuming there's a Product entity
import com.example.wheycenter.repository.ManufacturerRepository;
import com.example.wheycenter.repository.OriginRepository;
import com.example.wheycenter.repository.ProductRepository; // Assuming there's a ProductRepository
import com.example.wheycenter.response.ManufacturerResponse;
import com.example.wheycenter.response.OriginResponse;
import com.example.wheycenter.response.ProductFlavorResponse;
import com.example.wheycenter.response.ProductImageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OriginRepository originRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToProductResponse(product);
    }

    public ProductResponse createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setIsVisible(productDto.getIsVisible());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        // Fetch origin and manufacturer based on their IDs
        Origin origin = originRepository.findById(productDto.getOriginId())
                                    .orElseThrow(() -> new RuntimeException("Origin not found"));
        Manufacturer manufacturer = manufacturerRepository.findById(productDto.getManufacturerId())
                                                        .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
        
        // Set the fetched entities to the product
        product.setOrigin(origin);
        product.setManufacturer(manufacturer);

        // Save the product
        product = productRepository.save(product);

        return mapToProductResponse(product);
    }


    public ProductResponse updateProduct(String productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setIsVisible(productDto.getIsVisible());
        // Fetch origin and manufacturer based on their IDs
        Origin origin = originRepository.findById(productDto.getOriginId())
        .orElseThrow(() -> new RuntimeException("Origin not found"));
        Manufacturer manufacturer = manufacturerRepository.findById(productDto.getManufacturerId())
                            .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

        // Set the fetched entities to the product
        product.setOrigin(origin);
        product.setManufacturer(manufacturer);

        product.setUpdatedAt(new Date());
        product = productRepository.save(product);
        return mapToProductResponse(product);
    }

    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
    private ProductResponse mapToProductResponse(Product product) {
        // Map Manufacturer
        ManufacturerResponse manufacturer = new ManufacturerResponse(
                product.getManufacturer().getManufacturerId(),
                product.getManufacturer().getManufacturerName(),
                product.getManufacturer().getDescription(),
                product.getManufacturer().getCreatedAt(),
                product.getManufacturer().getUpdatedAt()
        );
    
        // Map Origin
        OriginResponse origin = new OriginResponse(
                product.getOrigin().getOriginId(),
                product.getOrigin().getCountry(),
                product.getOrigin().getCreatedAt(),
                product.getOrigin().getUpdatedAt()
        );
    
        // Map Product Flavors
        List<ProductFlavorResponse> flavors = product.getFlavors() != null ?
                product.getFlavors().stream().map(flavor -> new ProductFlavorResponse(
                        flavor.getProductFlavorId(),
                        flavor.getFlavorName(),
                        flavor.getStockQuantity()
                )).collect(Collectors.toList()) : Collections.emptyList();
    
        // Map Product Images
        List<ProductImageResponse> images = product.getImages() != null ?
                product.getImages().stream().map(image -> new ProductImageResponse(
                        image.getProductImageId(),
                        image.getImageUrl(),
                        image.getIsMain()
                )).collect(Collectors.toList()) : Collections.emptyList();
    
        // Map ProductResponse
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setDescription(product.getDescription());
        productResponse.setOriginalPrice(product.getOriginalPrice());
        productResponse.setUnitPrice(product.getUnitPrice());
        productResponse.setIsVisible(product.getIsVisible());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        productResponse.setManufacturer(manufacturer);
        productResponse.setOrigin(origin);
        productResponse.setFlavors(flavors);  // Set flavors
        productResponse.setImages(images);    // Set images
    
        // Return a list containing the single productResponse
        return productResponse;
    }
    

}
