package com.widehouse.resolutionkeeper.resolution.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.domain.ResolutionRepository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ResolutionServiceTest {
    private ResolutionService service;

    @Mock
    private ResolutionRepository resolutionRepository;

    @BeforeEach
    void setUp() {
        service = new ResolutionService(resolutionRepository);
    }

    @Test
    @DisplayName("id가 주어지면 해당 Resolution Mono 를 반환")
    void givenResolution_WhenGetById_ThenMonoResolution() {
        // given
        Resolution resolution = Resolution.builder()
                .id(10L)
                .name("test")
                .description("daybyday")
                .build();
        given(resolutionRepository.findById(anyLong()))
                .willReturn(Optional.of(resolution));
        // when
        Mono<Resolution> expected = service.get(10L);
        // then
        StepVerifier
                .create(expected)
                .expectNext(resolution)
                .expectComplete()
                .verify();
    }

    @Test
    void givenEmptyResolution_WhenGetById_ThenMonoEmpty() {
        // given
        given(resolutionRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        // when
        Mono<Resolution> expected = service.get(11L);
        // then
        StepVerifier
                .create(expected)
                .verifyComplete();
    }
}