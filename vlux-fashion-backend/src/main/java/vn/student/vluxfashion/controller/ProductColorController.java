package vn.student.vluxfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import vn.student.vluxfashion.dto.ProductColorDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.exception.FileUploadException;
import vn.student.vluxfashion.response.ProductColorResponse;
import vn.student.vluxfashion.service.ProductColorService;
import vn.student.vluxfashion.util.ValidationUtils;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product-colors")
public class ProductColorController {

    @Autowired
    private ProductColorService productColorService;

    @GetMapping
    public ResponseEntity<List<ProductColorResponse>> getAllProductColors() {
        List<ProductColorResponse> response = productColorService.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createProductColor(@ModelAttribute @Valid ProductColorDto productColorDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result));
        }
        try {
            ProductColorResponse productColor = productColorService.createProductColor(productColorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productColor);
        } catch (FileUploadException e) {
            throw new FileUploadException("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductColor(@PathVariable Integer id, @ModelAttribute @Valid ProductColorDto productColorDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result));
        }
        try {
            ProductColorResponse updatedColor = productColorService.updateProductColor(id, productColorDto);
            return ResponseEntity.ok(updatedColor);
        } catch (FileUploadException e) {
            throw new FileUploadException("Error uploading file: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product color not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductColor(@PathVariable Integer id) {
        try {
            productColorService.deleteProductColor(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Product color not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductColorResponse>> findByProductId(@PathVariable Integer productId) {
        List<ProductColorResponse> response = productColorService.findByProduct_ProductId(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductColorResponse> findProductColorById(@PathVariable Integer id) {
        ProductColorResponse response = productColorService.findById(id);
        return ResponseEntity.ok(response);
    }
}
