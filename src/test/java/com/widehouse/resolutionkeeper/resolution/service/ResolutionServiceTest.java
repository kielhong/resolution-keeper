package com.widehouse.resolutionkeeper.resolution.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.resolutionkeeper.resolution.model.Resolution;
import com.widehouse.resolutionkeeper.resolution.model.ResolutionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
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
                .id("10")
                .name("test")
                .description("daybyday")
                .build();
        given(resolutionRepository.findById(anyString()))
                .willReturn(Mono.just(resolution));
        // when
        Mono<Resolution> expected = service.get("10");
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
        given(resolutionRepository.findById(anyString()))
                .willReturn(Mono.empty());
        // when
        Mono<Resolution> actual = service.get("11");
        // then
        StepVerifier
                .create(actual)
                .verifyComplete();
    }

    @Test
    void givenList_WhenListAllSorted_ReturnSortedFlux() {
        // given
        Resolution r1 = Resolution.builder().id("1").name("r1").sortOrder(1).build();
        Resolution r2 = Resolution.builder().id("2").name("r2").sortOrder(2).build();
        Resolution r3 = Resolution.builder().id("3").name("r3").sortOrder(3).build();
        given(resolutionRepository.findAll(Sort.by("sortOrder")))
                .willReturn(Flux.just(r1, r2, r3));
        // when
        Flux<Resolution> actual = service.listAll(Sort.by("sortOrder"));
        // then
        StepVerifier
                .create(actual)
                .expectNext(r1)
                .expectNext(r2)
                .expectNext(r3)
                .expectComplete()
                .verify();
        verify(resolutionRepository).findAll(any(Sort.class));
    }

    @Test
    void givenResolution_WhenCreate_SaveAndReturnMono() {
        // given
        Resolution resolution = Resolution.builder()
                .name("test")
                .description("desc")
                .build();
        given(resolutionRepository.save(any(Resolution.class)))
                .willReturn(Mono.just(Resolution.builder().id("1").name("test").description("desc").build()));
        // when
        Mono<Resolution> actual = service.create(resolution);
        // then
        StepVerifier
                .create(actual)
                .expectNextMatches(r -> {
                    then(r)
                            .hasFieldOrPropertyWithValue("name", "test")
                            .hasFieldOrPropertyWithValue("description", "desc");
                    return true;
                })
                .expectComplete()
                .verify();
        verify(resolutionRepository).save(any(Resolution.class));
    }

    @Test
    void givenMatchedResolution_WhenRemove_ThenDeleteAResolution() {
        // when
        service.remove("13");
        // then
        verify(resolutionRepository).deleteById("13");
    }
}