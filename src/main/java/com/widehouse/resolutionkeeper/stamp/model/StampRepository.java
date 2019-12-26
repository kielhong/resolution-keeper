package com.widehouse.resolutionkeeper.stamp.model;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface StampRepository extends ReactiveCrudRepository<Stamp, String> {
    Flux<Stamp> findByResolutionId(String resolutionId);
}
