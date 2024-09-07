package com.example.wheycenter.controller;

import com.example.wheycenter.dto.ProductImageDto;
import com.example.wheycenter.response.ProductImageResponse;
import com.example.wheycenter.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ProductImageResponse> addImage(@ModelAttribute ProductImageDto productImageDto) throws IOException {
        ProductImageResponse createdImage = productImageService.addImage(productImageDto);
        return ResponseEntity.ok(createdImage);
    }

    @PutMapping("/{productImageId}")
    public ResponseEntity<Void> updateImage(@PathVariable Integer productImageId,
                                             @RequestBody ProductImageDto productImageDto) {
        productImageDto.setProductImageId(productImageId);
        productImageService.updateImage(productImageId, productImageDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer productImageId) {
        productImageService.deleteImage(productImageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductImageResponse>> getImagesByProductId(@PathVariable String productId) {
        List<ProductImageResponse> images = productImageService.getImagesByProductId(productId);
        return ResponseEntity.ok(images);
    }
}
