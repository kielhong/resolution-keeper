package com.widehouse.resolutionkeeper.stamp.service;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.model.StampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
        Stamp stamp = Stamp.builder()
                .resolutionId("r100")
                .build();
        given(stampRepository.save(any(Stamp.class)))
                .willReturn(Mono.just(Stamp.builder().id("i10").resolutionId("r100").createdAt(Instant.now()).build()));
        // when
        Mono<Stamp> actual = service.create(stamp);
        // then
        StepVerifier
                .create(actual)
                .expectNextMatches(r -> {
                    then(r)
                            .hasFieldOrPropertyWithValue("id", "i10")
                            .hasFieldOrPropertyWithValue("resolutionId", "r100")
                            .hasFieldOrProperty("createdAt");
                    return true;
                })
                .verifyComplete();
        verify(stampRepository).save(any(Stamp.class));
    }
}