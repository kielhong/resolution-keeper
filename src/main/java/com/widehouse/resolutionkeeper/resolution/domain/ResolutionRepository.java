package com.widehouse.resolutionkeeper.resolution.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ResolutionRepository extends ReactiveSortingRepository<Resolution, String> {
}

