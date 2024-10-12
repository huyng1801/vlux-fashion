package vn.student.vluxfashion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import vn.student.vluxfashion.dto.ProductDto;
import vn.student.vluxfashion.model.Gender;
import vn.student.vluxfashion.response.ProductResponse;
import vn.student.vluxfashion.service.ProductService;
import vn.student.vluxfashion.util.ValidationUtils;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
  // Get all products with optional filters: subCategoryId, gender, productName
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) Integer subCategoryId,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String productName) {

        // Call the service method with the filtering parameters
        List<ProductResponse> productResponses = productService.getAllProducts(subCategoryId, gender, productName);

        // Return the response wrapped in ResponseEntity
        return ResponseEntity.ok(productResponses);
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result)); // Handle validation errors
        }
        ProductResponse productResponse = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse); // Return 201 Created
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        if (productResponse == null) {
            return ResponseEntity.notFound().build(); // Handle not found case
        }
        return ResponseEntity.ok(productResponse);
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer productId, @Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result)); // Handle validation errors
        }
        ProductResponse productResponse = productService.updateProduct(productId, productDto);
        if (productResponse == null) {
            return ResponseEntity.notFound().build(); // Handle not found case
        }
        return ResponseEntity.ok(productResponse);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
