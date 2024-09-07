package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Product;
import com.example.wheycenter.model.ProductFlavor;
import com.example.wheycenter.repository.ProductFlavorRepository;
import com.example.wheycenter.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFlavorSeeder {

    private static final Logger logger = LoggerFactory.getLogger(ProductFlavorSeeder.class);

    @Bean
    CommandLineRunner seedProductFlavors(
            ProductFlavorRepository productFlavorRepository,
            ProductRepository productRepository) {

        return args -> {
            if (productFlavorRepository.count() == 0) {
                // Retrieve existing products
                Product superHuge = productRepository.findById("super-huge").orElseThrow(() -> new RuntimeException("Product 'super-huge' not found"));
                Product aminoKem = productRepository.findById("amino-kem").orElseThrow(() -> new RuntimeException("Product 'amino-kem' not found"));

                // Create and save product flavors
                ProductFlavor chocolate = new ProductFlavor();
                chocolate.setProduct(superHuge);
                chocolate.setFlavorName("Chocolate");
                chocolate.setStockQuantity(100);
                productFlavorRepository.save(chocolate);

                ProductFlavor bluberryApple = new ProductFlavor();
                bluberryApple.setProduct(aminoKem);
                bluberryApple.setFlavorName("Bluberry Apple");
                bluberryApple.setStockQuantity(50);
                productFlavorRepository.save(bluberryApple);

                logger.info("Product flavors seeded successfully!");
            }
        };
    }
}
