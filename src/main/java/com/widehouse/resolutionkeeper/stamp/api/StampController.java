package com.widehouse.resolutionkeeper.stamp.api;

import com.widehouse.resolutionkeeper.stamp.dto.StampDto;
import com.widehouse.resolutionkeeper.stamp.model.Stamp;
import com.widehouse.resolutionkeeper.stamp.service.StampService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stamps")
public class StampController {
    private StampService stampService;

    public StampController(StampService stampService) {
        this.stampService = stampService;
    }

    @PostMapping
    public Mono<Stamp> create(@RequestBody Stamp dto) {
        return stampService.create(dto);
    }

    @GetMapping
    public Flux<StampDto> listByResolution(@RequestParam String resolutionId) {
        return stampService.list(resolutionId);
    }

}
