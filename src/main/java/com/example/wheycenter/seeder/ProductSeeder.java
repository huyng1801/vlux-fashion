package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Category;
import com.example.wheycenter.model.Manufacturer;
import com.example.wheycenter.model.Origin;
import com.example.wheycenter.model.Product;
import com.example.wheycenter.repository.CategoryRepository;
import com.example.wheycenter.repository.ManufacturerRepository;
import com.example.wheycenter.repository.OriginRepository;
import com.example.wheycenter.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Component
public class ProductSeeder {

    private static final Logger logger = LoggerFactory.getLogger(ProductSeeder.class);

    @Bean
    CommandLineRunner seedProducts(
            ProductRepository productRepository,
            OriginRepository originRepository,
            ManufacturerRepository manufacturerRepository,
            CategoryRepository categoryRepository) {

        return args -> {
            if (productRepository.count() == 0) {
                // Retrieve existing entities
                Category massGainer = categoryRepository.findById("mass-gainer").orElseThrow(() -> new RuntimeException("Category 'mass-gainer' not found"));
                Category eaa = categoryRepository.findById("eaa").orElseThrow(() -> new RuntimeException("Category 'eaa' not found"));
                Origin origin = originRepository.findById(1).orElseThrow(() -> new RuntimeException("Origin with ID 1 not found"));
                Manufacturer evogen = manufacturerRepository.findById("evogen").orElseThrow(() -> new RuntimeException("Manufacturer 'evogen' not found"));

                // Create and save products
                Product superHuge = new Product();
                superHuge.setProductId("super-huge");
                superHuge.setProductName("Super Huge Gain – MASS Evogen tăng cân đẳng cấp nhất");
                superHuge.setDescription("Super Huge Gain chứa 50 gram nguồn protein cao cấp hoàn chỉnh tăng trưởng cơ bắp tối đa kết hợp nguồn carb phức, giàu xơ hạn chế lên mỡ. Mass cao năng lượng tăng cân nhanh hương vị thơm ngon thanh khiết.");
                superHuge.setOriginalPrice(BigDecimal.valueOf(2200000));
                superHuge.setUnitPrice(BigDecimal.valueOf(2200000));
                superHuge.setIsVisible(true);
                superHuge.setCreatedAt(new Date());
                superHuge.setUpdatedAt(new Date());
                superHuge.setOrigin(origin);
                superHuge.setManufacturer(evogen);
                superHuge.setCategories(Set.of(massGainer)); // Use existing category
                superHuge.setCreatedAt(new Date());
                superHuge.setUpdatedAt(new Date());

                Product aminoKem = new Product();
                aminoKem.setProductId("amino-kem");
                aminoKem.setProductName("AMINO K.E.M Nguồn Năng lượng tập luyện cao cấp nhất");
                aminoKem.setDescription("Trong một buổi tập luyện rất khó để bạn vừa tập trung, và đạt tối đa hiệu quả buổi tập bởi có rất nhiều yếu tố ảnh hưởng. Sức bền, mất nước là yếu tố đầu tiên ảnh hưởng đến bạn, trong quá trình tập luyện thì duy trì sức bền trong xuyên suốt quá trình tập luyện quả là rất khó vì khả năng hồi phục của cơ thể thường có sự chênh lệch rất lớn. Khi mệt mỏi, bạn rất khó để tập trung từ đó hiệu suất tập luyện không hiệu quả, dẫn đến quá trình rèn luyện của bạn bị bỏ phí rất nhiều.");
                aminoKem.setOriginalPrice(BigDecimal.valueOf(1330000));
                aminoKem.setUnitPrice(BigDecimal.valueOf(1330000));
                aminoKem.setIsVisible(true);
                aminoKem.setCreatedAt(new Date());
                aminoKem.setUpdatedAt(new Date());
                aminoKem.setOrigin(origin);
                aminoKem.setManufacturer(evogen);
                aminoKem.setCategories(Set.of(eaa)); // Use existing category
                aminoKem.setCreatedAt(new Date());
                aminoKem.setUpdatedAt(new Date());

                // Save products to the database
                productRepository.save(superHuge);
                productRepository.save(aminoKem);

                logger.info("Products seeded successfully!");
            }
        };
    }
}
