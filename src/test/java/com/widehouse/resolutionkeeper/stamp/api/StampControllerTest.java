package com.widehouse.resolutionkeeper.stamp.api;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.service.StampService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
}
