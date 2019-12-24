package com.widehouse.resolutionkeeper.resolution.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.service.ResolutionService;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ResolutionController.class)
class ResolutionControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ResolutionService resolutionService;

    @Test
    void testGetResolutionById() {
        // given
        Resolution resolution = Resolution.builder()
                .id("10")
                .name("test")
                .description("daybyday")
                .build();
        given(resolutionService.get("10"))
            .willReturn(Mono.just(resolution));
        // when
        webClient.get().uri("/resolutions/{id}", "10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("10")
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.description").isEqualTo("daybyday");
        // then
        verify(resolutionService).get("10");
    }

    @Test
    void givenMonoEmpty_WhenGetResolutionById_Then404() {
        // given
        given(resolutionService.get(anyString()))
                .willReturn(Mono.empty());
        // when
        webClient.get().uri("/resolutions/{id}", 10)
                .exchange()
                .expectStatus().isNotFound();
        // then
        verify(resolutionService).get("10");
    }

    @Test
    void whenGetAllResolutions_ThenListAll() {
        // given
        List<Resolution> list = Arrays.asList(
                Resolution.builder().id("1").name("r1").description("rd1").sortOrder(1).build(),
                Resolution.builder().id("2").name("r2").description("rd2").sortOrder(2).build(),
                Resolution.builder().id("3").name("r3").description("rd3").sortOrder(3).build());
        given(resolutionService.listAll(Sort.by("sortOrder")))
                .willReturn(Flux.fromIterable(list));
        // when
        webClient.get().uri("/resolutions")
                .exchange()
                .expectStatus().isOk();
        verify(resolutionService).listAll(any(Sort.class));
    }

    @Test
    void givenDto_WhenCreateResolution_ThenReturnCreatedResolution() {
        // given
        Resolution resolution = Resolution.builder()
                .name("test")
                .description("desc")
                .build();
        given(resolutionService.create(any(Resolution.class)))
                .willReturn(Mono.just(Resolution.builder().id("1").name("test").description("desc").build()));
        // when
        webClient.post().uri("/resolutions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(resolution))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.description").isEqualTo("desc");
        verify(resolutionService).create(any(Resolution.class));
    }

    @Test
    void givenResolution_WhenDeleteById_Then200Ok() {
        // when
        webClient.delete().uri("/resolutions/{id}", "13")
                .exchange()
                .expectStatus().isOk();
        verify(resolutionService).remove("13");
    }
}
