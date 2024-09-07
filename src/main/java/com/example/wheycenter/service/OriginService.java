package com.example.wheycenter.service;

import com.example.wheycenter.dto.OriginDto;
import com.example.wheycenter.model.Origin;
import com.example.wheycenter.repository.OriginRepository;
import com.example.wheycenter.response.OriginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OriginService {

    @Autowired
    private OriginRepository originRepository;

    public List<OriginResponse> getAllOrigins() {
        return originRepository.findAll().stream()
                .map(this::convertToOriginResponse)
                .collect(Collectors.toList());
    }

    public OriginResponse getOriginById(Integer originId) {
        return originRepository.findById(originId)
                .map(this::convertToOriginResponse)
                .orElse(null);
    }

    public OriginResponse createOrigin(OriginDto originDto) {
        Origin origin = new Origin();
        origin.setCountry(originDto.getCountry());
        origin.setCreatedAt(new Date());
        origin.setUpdatedAt(new Date());

        Origin savedOrigin = originRepository.save(origin);
        return convertToOriginResponse(savedOrigin);
    }

    public OriginResponse updateOrigin(Integer originId, OriginDto originDto) {
        Optional<Origin> optionalOrigin = originRepository.findById(originId);
        if (optionalOrigin.isEmpty()) {
            return null;
        }

        Origin origin = optionalOrigin.get();
        origin.setCountry(originDto.getCountry());
        origin.setUpdatedAt(new Date());

        Origin updatedOrigin = originRepository.save(origin);
        return convertToOriginResponse(updatedOrigin);
    }

    public void deleteOrigin(Integer originId) {
        originRepository.deleteById(originId);
    }

    private OriginResponse convertToOriginResponse(Origin origin) {
        return new OriginResponse(
                origin.getOriginId(),
                origin.getCountry(),
                origin.getCreatedAt(),
                origin.getUpdatedAt()
        );
    }
}
