package com.widehouse.resolutionkeeper.resolution.api;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;

import lombok.Getter;

@Getter
class ResolutionDto {
    private Long id;
    private String name;
    private String description;

    Resolution createEntity() {
        return Resolution.builder()
                .name(this.name)
                .description(this.description)
                .build();
    }
}
