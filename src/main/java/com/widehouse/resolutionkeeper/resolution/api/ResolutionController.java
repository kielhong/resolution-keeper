package com.widehouse.resolutionkeeper.resolution.api;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.service.ResolutionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("resolutions")
public class ResolutionController {
    private final ResolutionService resolutionService;

    @GetMapping("{id}")
    public Mono<Resolution> getById(@PathVariable Long id) {
        return resolutionService.get(id);
    }

}
