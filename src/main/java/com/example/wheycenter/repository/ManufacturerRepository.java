package com.example.wheycenter.repository;

import com.example.wheycenter.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {
}
