package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Origin;
import com.example.wheycenter.repository.OriginRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OriginDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(OriginDataSeeder.class);

    @Bean
    CommandLineRunner seedOrigins(OriginRepository originRepository) {
        return args -> {
            if (originRepository.count() == 0) { // To prevent duplicate entries

                // Create and save origins with Vietnamese data
                Origin origin1 = new Origin();
                origin1.setCountry("Mỹ"); // Vietnam name for USA
                origin1.setCreatedAt(new Date());
                origin1.setUpdatedAt(new Date());

                Origin origin2 = new Origin();
                origin2.setCountry("Nauy"); // Vietnam name for Norway
                origin2.setCreatedAt(new Date());
                origin2.setUpdatedAt(new Date());

                // Save origins to the database
                originRepository.save(origin1);
                originRepository.save(origin2);

                logger.info("Origins seeded successfully!");
            }
        };
    }
}
