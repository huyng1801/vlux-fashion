package com.example.wheycenter.controller;

import com.example.wheycenter.dto.ProductDto;
import com.example.wheycenter.response.ProductResponse;
import com.example.wheycenter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String productId) {
        ProductResponse product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductDto productDto) {
        ProductResponse createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") String productId, @RequestBody ProductDto productDto) {
        ProductResponse updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
