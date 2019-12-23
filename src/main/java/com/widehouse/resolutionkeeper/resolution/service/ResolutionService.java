package com.widehouse.resolutionkeeper.resolution.service;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.domain.ResolutionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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

    public Flux<Resolution> list() {
        return resolutionRepository.findAll();
    }

    public Mono<Resolution> create(Resolution resolution) {
        return resolutionRepository.save(resolution);
    }

    public Mono<Void> remove(String id) {
        return resolutionRepository.deleteById(id);
    }
}
