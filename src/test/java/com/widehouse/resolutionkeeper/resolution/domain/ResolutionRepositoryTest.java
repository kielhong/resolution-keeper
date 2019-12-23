package com.widehouse.resolutionkeeper.resolution.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
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
        Resolution actual = repository.save(resolution);
        // then
        then(actual)
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("name", "resolution")
                .hasFieldOrPropertyWithValue("description", "desc");
    }
}