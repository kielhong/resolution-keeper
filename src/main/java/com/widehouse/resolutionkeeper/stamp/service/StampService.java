package com.widehouse.resolutionkeeper.stamp.service;

import com.widehouse.resolutionkeeper.stamp.dto.StampDto;
import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.model.StampRepository;

import java.util.Comparator;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StampService {
    private StampRepository stampRepository;

    public StampService(StampRepository stampRepository) {
        this.stampRepository = stampRepository;
    }

    public Mono<Stamp> create(Stamp stamp) {
        return stampRepository.save(stamp);
    }

    public Flux<StampDto> list(String resolutionsId) {
        return stampRepository.findByResolutionId(resolutionsId)
                .map(StampDto::from)
                .distinct(StampDto::getCreatedDate)
                .sort(Comparator.comparing(StampDto::getCreatedDate));
    }
}
