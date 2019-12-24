package com.widehouse.resolutionkeeper.resolution.api;

import com.widehouse.resolutionkeeper.resolution.domain.Resolution;

import lombok.Getter;

@Getter
class ResolutionDto {
    private String id;
    private String name;
    private String description;
    private int sortOrder;

    Resolution createEntity() {
        return Resolution.builder()
                .name(this.name)
                .description(this.description)
                .sortOrder(this.sortOrder)
                .build();
    }

    public static ResolutionDto from(Resolution resolution) {
        ResolutionDto dto = new ResolutionDto();
        dto.id = resolution.getId();
        dto.name = resolution.getName();
        dto.description = resolution.getDescription();
        dto.sortOrder = resolution.getSortOrder();

        return dto;
    }
}
