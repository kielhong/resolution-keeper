package com.widehouse.resolutionkeeper.stamp.dto;

import static org.assertj.core.api.BDDAssertions.then;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

class StampDtoTest {
    @Test
    void generateDtoTest() {
        // given
        Stamp stamp = Stamp
                .builder()
                .id("1")
                .resolutionId("r1")
                .createdAt(Instant.parse("2019-11-01T00:00:00Z"))
                .build();
        // when
        StampDto actual = StampDto.from(stamp, ZoneId.of("Asia/Seoul"));
        // then
        then(actual)
                .hasFieldOrPropertyWithValue("id", "1")
                .hasFieldOrPropertyWithValue("resolutionId", "r1")
                .hasFieldOrPropertyWithValue("stampDate", LocalDate.of(2019, 11, 1));
    }
}