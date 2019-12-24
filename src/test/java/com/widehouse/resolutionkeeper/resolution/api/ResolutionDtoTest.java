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
        ReflectionTestUtils.setField(dto, "sortOrder", 5);
        // when
        Resolution actual = dto.createEntity();
        // then
        then(actual)
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("sortOrder", 5);
    }

    @Test
    void testFromEntity() {
        // given
        Resolution resolution = Resolution.builder()
                .id("10")
                .name("test")
                .description("desc")
                .sortOrder(6)
                .build();
        // when
        ResolutionDto actual = ResolutionDto.from(resolution);
        // then
        then(actual)
                .hasFieldOrPropertyWithValue("id", "10")
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("description", "desc")
                .hasFieldOrPropertyWithValue("sortOrder", 6);
    }
}