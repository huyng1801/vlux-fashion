package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Banner;
import com.example.wheycenter.repository.BannerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BannerDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(BannerDataSeeder.class);

    @Bean
    CommandLineRunner seedBanners(BannerRepository bannerRepository) {
        return args -> {
            if (bannerRepository.count() == 0) { // To prevent duplicate entries

                // Create and save banners with example data
                Banner banner1 = new Banner();
                banner1.setTitle("Lean Body");
                banner1.setImageUrl("https://s3-wheycenter.s3.ap-southeast-2.amazonaws.com/wheycenter/banner/66cc9a5744867.jpg");
                banner1.setLink("https://www.linkedin.com/in/huy-nguyen-088464266/");
                banner1.setIsVisible(true);
                banner1.setCreatedAt(new Date());
                banner1.setUpdatedAt(new Date());

                Banner banner2 = new Banner();
                banner2.setTitle("Evogen");
                banner2.setImageUrl("https://s3-wheycenter.s3.ap-southeast-2.amazonaws.com/wheycenter/banner/66cc9c4d490a7.jpg");
                banner2.setLink("https://www.linkedin.com/in/huy-nguyen-088464266/");
                banner2.setIsVisible(true);
                banner2.setCreatedAt(new Date());
                banner2.setUpdatedAt(new Date());
                
                // Save banners to the database
                bannerRepository.save(banner1);
                bannerRepository.save(banner2);
               

                logger.info("Banners seeded successfully!");
            }
        };
    }
}
