package com.widehouse.resolutionkeeper.stamp.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.resolutionkeeper.stamp.dto.StampDto;
import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.model.StampRepository;

import java.time.Instant;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class StampServiceTest {
    private StampService service;
    @Mock
    private StampRepository stampRepository;

    @BeforeEach
    void setUp() {
        service = new StampService(stampRepository);
    }

    @Test
    void givenStamp_WhenCreate_ThenSaveStampAndReturnSavedStamp() {
        // given
        Stamp fixture = Stamp.builder().resolutionId("r100").build();
        Stamp expected = Stamp.builder().id("i10").resolutionId("r100").createdAt(Instant.now()).build();
        given(stampRepository.save(any(Stamp.class)))
                .willReturn(Mono.just(expected));
        // when
        Mono<Stamp> actual = service.create(fixture);
        // then
        StepVerifier
                .create(actual)
                .expectNextMatches(r -> {
                    then(r).isEqualTo(expected);
                    return true;
                })
                .verifyComplete();
        verify(stampRepository).save(any(Stamp.class));
    }

    @Nested
    @DisplayName("when list")
    class ListFluxStampDto {
        private Stamp stamp1;
        private Stamp stamp2;
        private Stamp stamp3;

        @BeforeEach
        void setUp() {
            stamp1 = Stamp.builder()
                    .id("1").resolutionId("r100").createdAt(Instant.parse("2019-11-01T00:00:00Z")).build();
            stamp2 = Stamp.builder()
                    .id("2").resolutionId("r100").createdAt(Instant.parse("2019-11-02T00:00:00Z")).build();
            stamp3 = Stamp.builder()
                    .id("3").resolutionId("r100").createdAt(Instant.parse("2019-11-03T00:00:00Z")).build();
        }

        @Test
        @DisplayName("return Flux StampDto")
        void givenStampsOfResolutions_When_listByResolution_Then_ReturnFluxStampDto() {
            // given
            given(stampRepository.findByResolutionId(anyString()))
                    .willReturn(Flux.just(stamp1, stamp2, stamp3));
            // when
            Flux<StampDto> result = service.list("r100");
            // then
            StepVerifier
                    .create(result)
                    .assertNext(r -> then(r).extracting("id").isEqualTo("1"))
                    .assertNext(r -> then(r).extracting("id").isEqualTo("2"))
                    .assertNext(r -> then(r).extracting("id").isEqualTo("3"))
                    .verifyComplete();
            verify(stampRepository).findByResolutionId("r100");
        }

        @Test
        @DisplayName("list distinct created date")
        void then_returnDistinctCreatedDate() {
            // given
            Stamp stamp1 = Stamp.builder()
                    .id("1").resolutionId("r100").createdAt(Instant.parse("2019-11-01T09:00:00Z")).build();
            Stamp stamp2 = Stamp.builder()
                    .id("2").resolutionId("r100").createdAt(Instant.parse("2019-11-01T11:00:00Z")).build();
            given(stampRepository.findByResolutionId(anyString()))
                    .willReturn(Flux.just(stamp1, stamp2));
            // when
            Flux<StampDto> result = service.list("r100");
            // then
            StepVerifier
                    .create(result)
                    .assertNext(r -> then(r.getStampDate()).isEqualTo(LocalDate.of(2019, 11, 1)))
                    .verifyComplete();
        }

        @Test
        @DisplayName("list sorted by created date")
        void given_UnsortedStamps_then_returnSortByCreatedDate() {
            // given
            given(stampRepository.findByResolutionId(anyString()))
                    .willReturn(Flux.just(stamp3, stamp2, stamp1));
            // when
            Flux<StampDto> result = service.list("r100");
            // then
            StepVerifier
                    .create(result)
                    .assertNext(r -> then(r).extracting("stampDate").isEqualTo(LocalDate.of(2019, 11, 1)))
                    .assertNext(r -> then(r).extracting("stampDate").isEqualTo(LocalDate.of(2019, 11, 2)))
                    .assertNext(r -> then(r).extracting("stampDate").isEqualTo(LocalDate.of(2019, 11, 3)))
                    .verifyComplete();
            verify(stampRepository).findByResolutionId("r100");
        }
    }

}