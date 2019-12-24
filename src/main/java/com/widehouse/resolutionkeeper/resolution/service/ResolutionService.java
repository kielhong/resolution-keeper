package com.widehouse.resolutionkeeper.resolution.service;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.domain.ResolutionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ResolutionService {
    private final ResolutionRepository resolutionRepository;

    /**
     * get a Resolution by Id.
     * @param id resolution id
     */
    public Mono<Resolution> get(String id) {
        return resolutionRepository.findById(id);
    }

    public Flux<Resolution> listAll(Sort sort) {
        return resolutionRepository.findAll(sort);
    }

    public Mono<Resolution> create(Resolution resolution) {
        return resolutionRepository.save(resolution);
    }

    public Mono<Void> remove(String id) {
        return resolutionRepository.deleteById(id);
    }
}
