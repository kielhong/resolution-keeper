package com.widehouse.resolutionkeeper.resolution.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResolutionRepository extends MongoRepository<Resolution, String> {
}

