package com.widehouse.resolutionkeeper.resolution.domain;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.resolutionkeeper.config.MongoConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@Import(MongoConfig.class)
class ResolutionRepositoryTest {
    @Autowired
    private ResolutionRepository repository;

    @Test
    void testSave() {
        // given
        Resolution resolution = Resolution.builder()
                .name("resolution")
                .description("desc")
                .build();
        // when
        Mono<Resolution> actual = repository.save(resolution);
        // then
        StepVerifier
                .create(actual)
                .assertNext(result -> then(result)
                        .hasFieldOrProperty("id")
                        .hasFieldOrPropertyWithValue("name", "resolution")
                        .hasFieldOrPropertyWithValue("description", "desc")
                        .hasFieldOrProperty("createdAt")
                        .hasFieldOrProperty("updatedAt")
                )
                .expectComplete()
                .verify();
    }
}