package com.widehouse.resolutionkeeper.resolution.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.domain.ResolutionRepository;

import java.util.Optional;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

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
    void testGetResolutionMonoById() {
        // given
        Resolution resolution = Resolution.builder()
                .id(10L)
                .name("test")
                .description("daybyday")
                .build();
        given(resolutionRepository.findById(any()))
                .willReturn(Optional.of(resolution));
        // when
        Mono<Resolution> expected = service.get(10L);
        // then
        then(expected.block())
                .isEqualTo(resolution);
    }
}