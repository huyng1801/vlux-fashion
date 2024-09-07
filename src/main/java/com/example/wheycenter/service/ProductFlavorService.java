package com.example.wheycenter.service;

import com.example.wheycenter.dto.ProductFlavorDto;
import com.example.wheycenter.model.Product;
import com.example.wheycenter.model.ProductFlavor;
import com.example.wheycenter.repository.ProductFlavorRepository;
import com.example.wheycenter.repository.ProductRepository;
import com.example.wheycenter.response.ProductFlavorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductFlavorService {

    @Autowired
    private ProductFlavorRepository productFlavorRepository;

    @Autowired
    private ProductRepository productRepository;


    public ProductFlavorResponse createFlavor(ProductFlavorDto productFlavorDto) {
        ProductFlavor productFlavor = new ProductFlavor();
        productFlavor.setFlavorName(productFlavorDto.getFlavorName());
        productFlavor.setStockQuantity(productFlavorDto.getStockQuantity());

        Product product = productRepository.findById(productFlavorDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productFlavor.setProduct(product);

        ProductFlavor savedFlavor = productFlavorRepository.save(productFlavor);
        return mapToResponse(savedFlavor);
    }

    public ProductFlavorResponse updateFlavor(Integer flavorId, ProductFlavorDto productFlavorDto) {
        ProductFlavor productFlavor = productFlavorRepository.findById(flavorId)
                .orElseThrow(() -> new RuntimeException("Flavor not found"));

        productFlavor.setFlavorName(productFlavorDto.getFlavorName());
        productFlavor.setStockQuantity(productFlavorDto.getStockQuantity());

        Product product = productRepository.findById(productFlavorDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productFlavor.setProduct(product);

        ProductFlavor updatedFlavor = productFlavorRepository.save(productFlavor);
        return mapToResponse(updatedFlavor);
    }

    public void deleteFlavor(Integer flavorId) {
        if (!productFlavorRepository.existsById(flavorId)) {
            throw new RuntimeException("Flavor not found");
        }
        productFlavorRepository.deleteById(flavorId);
    }

    public ProductFlavorResponse getFlavorById(Integer flavorId) {
        ProductFlavor productFlavor = productFlavorRepository.findById(flavorId)
                .orElseThrow(() -> new RuntimeException("Flavor not found"));
        return mapToResponse(productFlavor);
    }

    public List<ProductFlavorResponse> getFlavorsByProductId(String productId) {
        List<ProductFlavor> productFlavors = productFlavorRepository.findByProduct_ProductId(productId);
        return productFlavors.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductFlavorResponse mapToResponse(ProductFlavor productFlavor) {
        return new ProductFlavorResponse(
                productFlavor.getProductFlavorId(),
                productFlavor.getFlavorName(),
                productFlavor.getStockQuantity()
        );
    }
}
