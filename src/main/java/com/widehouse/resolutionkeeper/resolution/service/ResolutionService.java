package com.widehouse.resolutionkeeper.resolution.service;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.domain.ResolutionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class ResolutionService {
    private final ResolutionRepository resolutionRepository;

    /**
     * get a Resolution by Id.
     * @param id resolution id
     */
    public Mono<Resolution> get(long id) {
        return Mono.fromCallable(() -> resolutionRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty));
    }

    public Flux<Resolution> list() {
        return Flux.fromIterable(resolutionRepository.findAll());
    }

    public Mono<Resolution> create(Resolution resolution) {
        return Mono.just(resolutionRepository.save(resolution));
    }
}
