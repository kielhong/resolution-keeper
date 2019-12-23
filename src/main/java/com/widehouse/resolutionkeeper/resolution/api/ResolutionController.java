package com.widehouse.resolutionkeeper.resolution.api;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;
import com.widehouse.resolutionkeeper.resolution.service.ResolutionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("resolutions")
public class ResolutionController {
    private final ResolutionService resolutionService;

    @GetMapping
    public Flux<Resolution> getAllResolutions() {
        return resolutionService.list();
    }

    /**
     * get Mono Resolution by id.
     * if Resolution is not found then 404 Not Found
     * @param id id of Resolution
     */
    @GetMapping("{id}")
    public Mono<Resolution> getById(@PathVariable String id) {
        return resolutionService.get(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Resolution:" + id + " Not Found")));
    }

    @PostMapping
    public Mono<Resolution> create(@RequestBody ResolutionDto dto) {
        return resolutionService.create(dto.createEntity());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        System.out.println("id=" + id);
        return resolutionService.remove(id);
    }
}
