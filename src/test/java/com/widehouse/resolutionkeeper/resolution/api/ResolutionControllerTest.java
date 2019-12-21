package com.widehouse.resolutionkeeper.resolution.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.service.ResolutionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
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
                .id(10L)
                .name("test")
                .description("daybyday")
                .build();
        given(resolutionService.get(10L))
            .willReturn(Mono.just(resolution));
        // when
        webClient.get().uri("/resolutions/{id}", 10)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(10)
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.description").isEqualTo("daybyday");
        // then
        verify(resolutionService).get(10L);
    }
}
