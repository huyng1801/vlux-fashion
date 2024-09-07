package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Product;
import com.example.wheycenter.model.ProductImage;
import com.example.wheycenter.repository.ProductImageRepository;
import com.example.wheycenter.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductImageSeeder {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageSeeder.class);

    @Bean
    CommandLineRunner seedProductImages(
            ProductImageRepository productImageRepository,
            ProductRepository productRepository) {

        return args -> {
            if (productImageRepository.count() == 0) {
                // Retrieve existing products
                Product superHuge = productRepository.findById("super-huge").orElseThrow(() -> new RuntimeException("Product 'super-huge' not found"));
                Product aminoKem = productRepository.findById("amino-kem").orElseThrow(() -> new RuntimeException("Product 'amino-kem' not found"));

                // Create and save product images
                ProductImage image1 = new ProductImage();
                image1.setProduct(superHuge);
                image1.setImageUrl("https://s3-wheycenter.s3.ap-southeast-2.amazonaws.com/wheycenter/product/66cd2345a36a7.jpg");
                image1.setIsMain(true);
                productImageRepository.save(image1);

                ProductImage image2 = new ProductImage();
                image2.setProduct(aminoKem);
                image2.setImageUrl("https://s3-wheycenter.s3.ap-southeast-2.amazonaws.com/wheycenter/product/66cd22fae7027.jpg");
                image2.setIsMain(true);
                productImageRepository.save(image2);

                logger.info("Product images seeded successfully!");
            }
        };
    }
}
