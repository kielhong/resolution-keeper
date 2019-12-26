package com.widehouse.resolutionkeeper.stamp.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.service.StampService;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(StampController.class)
class StampControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private StampService stampService;

    @Test
    void givenStamp_whenCreateStamp_Then200OkAndReturnCreatedStampDto() {
        // given
        Stamp stamp = Stamp.builder()
                .resolutionId("r100")
                .build();
        given(stampService.create(any(Stamp.class)))
                .willReturn(Mono.just(Stamp.builder().id("10").resolutionId("r100").createdAt(Instant.now()).build()));
        // when
        webClient.post().uri("/stamps")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(stamp))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.resolutionId").isEqualTo("r100")
                .jsonPath("$.createdAt").exists();
        verify(stampService).create(any(Stamp.class));
    }

    @Test
    void givenResolutionId_WhenGetStamps_ThenListStamps() {
        // given
        Stamp stamp1 = Stamp.builder().id("1").resolutionId("r100").build();
        Stamp stamp2 = Stamp.builder().id("2").resolutionId("r100").build();
        Stamp stamp3 = Stamp.builder().id("3").resolutionId("r100").build();
        given(stampService.list(anyString()))
                .willReturn(Flux.just(stamp1, stamp2, stamp3));
        // when
        webClient.get().uri("/stamps?resolutionId={rId}", "r100")
                .exchange()
                .expectStatus().isOk();
        // then
        verify(stampService).list("r100");
    }
}
