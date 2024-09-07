package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Manufacturer;
import com.example.wheycenter.repository.ManufacturerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ManufacturerDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(ManufacturerDataSeeder.class);

    @Bean
    CommandLineRunner seedManufacturers(ManufacturerRepository manufacturerRepository) {
        return args -> {
            if (manufacturerRepository.count() == 0) { // To prevent duplicate entries

                // Create and save manufacturers with Vietnamese data
                Manufacturer labrada = new Manufacturer();
                labrada.setManufacturerId("labrada");
                labrada.setManufacturerName("LABRADA");
                labrada.setDescription("Nhà sản xuất thực phẩm bổ sung hàng đầu tại Hoa Kỳ.");
                labrada.setCreatedAt(new Date());
                labrada.setUpdatedAt(new Date());

                Manufacturer evogen = new Manufacturer();
                evogen.setManufacturerId("evogen");
                evogen.setManufacturerName("EVOGEN");
                evogen.setDescription("Thương hiệu quốc tế cung cấp các sản phẩm bổ sung chất lượng cao.");
                evogen.setCreatedAt(new Date());
                evogen.setUpdatedAt(new Date());

                Manufacturer nortech = new Manufacturer();
                nortech.setManufacturerId("nortech");
                nortech.setManufacturerName("Nortech");
                nortech.setDescription("Cung cấp các sản phẩm dinh dưỡng thể thao và sức khỏe.");
                nortech.setCreatedAt(new Date());
                nortech.setUpdatedAt(new Date());

                // Save manufacturers to the database
                manufacturerRepository.save(labrada);
                manufacturerRepository.save(evogen);
                manufacturerRepository.save(nortech);

                logger.info("Manufacturers seeded successfully!");
            }
        };
    }
}
