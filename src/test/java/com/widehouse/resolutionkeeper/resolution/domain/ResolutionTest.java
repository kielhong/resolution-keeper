package com.widehouse.resolutionkeeper.resolution.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class ResolutionTest {
    @Test
    void buildResolutionTest() {
        Resolution actual = Resolution.builder()
                .id("1")
                .name("resolution")
                .description("daily resolution")
                .build();

        then(actual)
                .hasFieldOrPropertyWithValue("id", "1")
                .hasFieldOrPropertyWithValue("name", "resolution")
                .hasFieldOrPropertyWithValue("description", "daily resolution");
    }
}
