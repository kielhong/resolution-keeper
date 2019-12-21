package com.widehouse.resolutionkeeper.resolution.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class ResolutionTest {

    @Test
    void buildResolutionTest() {
        Resolution expected = Resolution.builder()
                .id(1L)
                .name("resolution")
                .description("daily resolution")
                .build();

        then(expected)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "resolution")
                .hasFieldOrPropertyWithValue("description", "daily resolution");
    }
}
