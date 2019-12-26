package com.widehouse.resolutionkeeper.stamp.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.model.StampRepository;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void givenStampsOfResolutions_When_listByResolution_Then_ReturnFluxStamps() {
        // given
        Stamp stamp1 = Stamp.builder().id("1").resolutionId("r100").build();
        Stamp stamp2 = Stamp.builder().id("2").resolutionId("r100").build();
        Stamp stamp3 = Stamp.builder().id("3").resolutionId("r100").build();
        given(stampRepository.findByResolutionId(anyString()))
                .willReturn(Flux.just(stamp1, stamp2, stamp3));
        // when
        Flux<Stamp> result = service.list("r100");
        // then
        StepVerifier
                .create(result)
                .expectNext(stamp1, stamp2, stamp3)
                .verifyComplete();
        verify(stampRepository).findByResolutionId("r100");
    }
}