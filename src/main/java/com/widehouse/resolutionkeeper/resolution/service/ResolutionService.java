package com.widehouse.resolutionkeeper.resolution.service;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.domain.ResolutionRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ResolutionService {
    private final ResolutionRepository resolutionRepository;

    public Mono<Resolution> get(long id) {
        Optional<Resolution> optionalResolution = resolutionRepository.findById(id);
        // TODO : optional empty case
        return Mono.just(optionalResolution.get());
    }
}
