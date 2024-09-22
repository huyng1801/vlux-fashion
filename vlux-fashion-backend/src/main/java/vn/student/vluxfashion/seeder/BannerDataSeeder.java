package vn.student.vluxfashion.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import vn.student.vluxfashion.model.Banner;
import vn.student.vluxfashion.repository.BannerRepository;

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
                banner1.setTitle("co logo");
                banner1.setImageUrl("https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/banner/1920x625_co_logo__sun_new_2_0509.webp");
                banner1.setLink("https://www.linkedin.com/in/huyng1801");
                banner1.setIsVisible(true);
                banner1.setCreatedAt(new Date());
                banner1.setUpdatedAt(new Date());

                Banner banner2 = new Banner();
                banner2.setTitle("guess");
                banner2.setImageUrl("https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/banner/1920x625_guess_new_1009_1.webp");
                banner2.setLink("https://www.linkedin.com/in/huyng1801");
                banner2.setIsVisible(true);
                banner2.setCreatedAt(new Date());
                banner2.setUpdatedAt(new Date());

                Banner banner3 = new Banner();
                banner3.setTitle("new ecom");
                banner3.setImageUrl("https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/banner/New_1920x625_ecom_2908.webp");
                banner3.setLink("https://www.linkedin.com/in/huyng1801");
                banner3.setIsVisible(true);
                banner3.setCreatedAt(new Date());
                banner3.setUpdatedAt(new Date());

                Banner banner4 = new Banner();
                banner4.setTitle("on fall");
                banner4.setImageUrl("https://huyngaws.s3.ap-southeast-2.amazonaws.com/vlux-fashion/banner/ON_FALL_2024_1920x625px_2008_1.webp");
                banner4.setLink("https://www.linkedin.com/in/huyng1801");
                banner4.setIsVisible(true);
                banner4.setCreatedAt(new Date());
                banner4.setUpdatedAt(new Date());
                
                // Save banners to the database
                bannerRepository.save(banner1);
                bannerRepository.save(banner2);
                bannerRepository.save(banner3);
                bannerRepository.save(banner4);
               
               
                logger.info("Banners seeded successfully!");
            }
        };
    }
}
