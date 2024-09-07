package com.example.wheycenter.service;

import com.example.wheycenter.dto.ManufacturerDto;
import com.example.wheycenter.model.Manufacturer;
import com.example.wheycenter.repository.ManufacturerRepository;
import com.example.wheycenter.response.ManufacturerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<ManufacturerResponse> getAllManufacturers() {
        return manufacturerRepository.findAll().stream()
                .map(this::convertToManufacturerResponse)
                .collect(Collectors.toList());
    }

    public ManufacturerResponse getManufacturerById(String manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .map(this::convertToManufacturerResponse)
                .orElse(null);
    }

    public ManufacturerResponse createManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(manufacturerDto.getManufacturerId()); // Generate a unique ID
        manufacturer.setManufacturerName(manufacturerDto.getManufacturerName());
        manufacturer.setDescription(manufacturerDto.getDescription());
        manufacturer.setCreatedAt(new Date());
        manufacturer.setUpdatedAt(new Date());

        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return convertToManufacturerResponse(savedManufacturer);
    }

    public ManufacturerResponse updateManufacturer(String manufacturerId, ManufacturerDto manufacturerDto) {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturerId);
        if (optionalManufacturer.isEmpty()) {
            return null;
        }

        Manufacturer manufacturer = optionalManufacturer.get();
        manufacturer.setManufacturerName(manufacturerDto.getManufacturerName());
        manufacturer.setDescription(manufacturerDto.getDescription());
        manufacturer.setUpdatedAt(new Date());

        Manufacturer updatedManufacturer = manufacturerRepository.save(manufacturer);
        return convertToManufacturerResponse(updatedManufacturer);
    }

    public void deleteManufacturer(String manufacturerId) {
        manufacturerRepository.deleteById(manufacturerId);
    }

    private ManufacturerResponse convertToManufacturerResponse(Manufacturer manufacturer) {
        return new ManufacturerResponse(
                manufacturer.getManufacturerId(),
                manufacturer.getManufacturerName(),
                manufacturer.getDescription(),
                manufacturer.getCreatedAt(),
                manufacturer.getUpdatedAt()
        );
    }
}
