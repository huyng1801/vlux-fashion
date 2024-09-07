package com.example.wheycenter.controller;

import com.example.wheycenter.dto.OriginDto;
import com.example.wheycenter.response.OriginResponse;
import com.example.wheycenter.service.OriginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/origins")
public class OriginController {

    @Autowired
    private OriginService originService;

    @GetMapping
    public ResponseEntity<List<OriginResponse>> getAllOrigins() {
        List<OriginResponse> origins = originService.getAllOrigins();
        return new ResponseEntity<>(origins, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OriginResponse> getOriginById(@PathVariable("id") Integer originId) {
        OriginResponse originResponse = originService.getOriginById(originId);
        return originResponse != null ? ResponseEntity.ok(originResponse) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OriginResponse> createOrigin(@Valid @RequestBody OriginDto originDto) {
        OriginResponse createdOrigin = originService.createOrigin(originDto);
        return new ResponseEntity<>(createdOrigin, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OriginResponse> updateOrigin(@PathVariable("id") Integer originId,
                                                       @Valid @RequestBody OriginDto originDto) {
        OriginResponse updatedOrigin = originService.updateOrigin(originId, originDto);
        return updatedOrigin != null ? ResponseEntity.ok(updatedOrigin) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrigin(@PathVariable("id") Integer originId) {
        originService.deleteOrigin(originId);
        return ResponseEntity.noContent().build();
    }
}
