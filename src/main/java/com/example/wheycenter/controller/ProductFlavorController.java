package com.example.wheycenter.controller;

import com.example.wheycenter.dto.ProductFlavorDto;
import com.example.wheycenter.response.ProductFlavorResponse;
import com.example.wheycenter.service.ProductFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flavors")
public class ProductFlavorController {

    @Autowired
    private ProductFlavorService productFlavorService;

    @PostMapping
    public ResponseEntity<ProductFlavorResponse> createFlavor(@RequestBody ProductFlavorDto productFlavorDto) {
        ProductFlavorResponse createdFlavor = productFlavorService.createFlavor(productFlavorDto);
        return ResponseEntity.ok(createdFlavor);
    }

    @PutMapping("/{flavorId}")
    public ResponseEntity<ProductFlavorResponse> updateFlavor(@PathVariable Integer flavorId, @RequestBody ProductFlavorDto productFlavorDto) {
        ProductFlavorResponse updatedFlavor = productFlavorService.updateFlavor(flavorId, productFlavorDto);
        return ResponseEntity.ok(updatedFlavor);
    }

    @DeleteMapping("/{flavorId}")
    public ResponseEntity<Void> deleteFlavor(@PathVariable Integer flavorId) {
        productFlavorService.deleteFlavor(flavorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{flavorId}")
    public ResponseEntity<ProductFlavorResponse> getFlavorById(@PathVariable Integer flavorId) {
        ProductFlavorResponse flavorResponse = productFlavorService.getFlavorById(flavorId);
        return ResponseEntity.ok(flavorResponse);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductFlavorResponse>> getFlavorsByProductId(@PathVariable String productId) {
        List<ProductFlavorResponse> flavors = productFlavorService.getFlavorsByProductId(productId);
        return ResponseEntity.ok(flavors);
    }
}
