package com.example.wheycenter.controller;

import com.example.wheycenter.dto.ManufacturerDto;
import com.example.wheycenter.response.ManufacturerResponse;
import com.example.wheycenter.service.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerResponse>> getAllManufacturers() {
        List<ManufacturerResponse> manufacturers = manufacturerService.getAllManufacturers();
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponse> getManufacturerById(@PathVariable("id") String manufacturerId) {
        ManufacturerResponse manufacturerResponse = manufacturerService.getManufacturerById(manufacturerId);
        return manufacturerResponse != null ? ResponseEntity.ok(manufacturerResponse) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ManufacturerResponse> createManufacturer(@Valid @RequestBody ManufacturerDto manufacturerDto) {
        ManufacturerResponse createdManufacturer = manufacturerService.createManufacturer(manufacturerDto);
        return new ResponseEntity<>(createdManufacturer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerResponse> updateManufacturer(@PathVariable("id") String manufacturerId,
                                                                     @Valid @RequestBody ManufacturerDto manufacturerDto) {
        ManufacturerResponse updatedManufacturer = manufacturerService.updateManufacturer(manufacturerId, manufacturerDto);
        return updatedManufacturer != null ? ResponseEntity.ok(updatedManufacturer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable("id") String manufacturerId) {
        manufacturerService.deleteManufacturer(manufacturerId);
        return ResponseEntity.noContent().build();
    }
}
