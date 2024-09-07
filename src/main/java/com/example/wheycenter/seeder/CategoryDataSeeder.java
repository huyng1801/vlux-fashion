package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Category;
import com.example.wheycenter.repository.CategoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CategoryDataSeeder {
 private static final Logger logger = LoggerFactory.getLogger(CategoryDataSeeder.class);

    @Bean
    CommandLineRunner seedCategories(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) { // To prevent duplicate entries
                
            // Create and save categories with Vietnamese names
            Category wheyProtein = new Category();
            wheyProtein.setCategoryId("whey-protein");
            wheyProtein.setCategoryName("Whey Protein");

            Category massGainer = new Category();
            massGainer.setCategoryId("mass-gainer");
            massGainer.setCategoryName("Mass Gainer");

            Category preWorkout = new Category();
            preWorkout.setCategoryId("pre-workout");
            preWorkout.setCategoryName("Pre-Workout");

            Category eaa = new Category();
            eaa.setCategoryId("eaa");
            eaa.setCategoryName("EAA");

            // Save categories to the database
            categoryRepository.save(wheyProtein);
            categoryRepository.save(massGainer);
            categoryRepository.save(preWorkout);
            categoryRepository.save(eaa);

            logger.info("Categories seeded successfully!");
        }
    };
}
}
