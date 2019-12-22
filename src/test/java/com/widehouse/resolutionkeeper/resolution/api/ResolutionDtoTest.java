package com.widehouse.resolutionkeeper.resolution.api;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class ResolutionDtoTest {
    @Test
    void testCreateEntity() {
        // given
        ResolutionDto dto = new ResolutionDto();
        ReflectionTestUtils.setField(dto, "name", "test");
        ReflectionTestUtils.setField(dto, "description", "desc");
        // when
        Resolution actual = dto.createEntity();
        // then
        then(actual)
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("description", "desc");
    }
}