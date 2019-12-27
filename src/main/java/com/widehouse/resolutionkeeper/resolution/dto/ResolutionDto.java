package com.widehouse.resolutionkeeper.resolution.dto;

import com.widehouse.resolutionkeeper.resolution.model.Resolution;

import lombok.Getter;

@Getter
public class ResolutionDto {
    private String id;
    private String name;
    private String description;
    private int sortOrder;

    /**
     * create Resolution Entity from this object.
     * @return Created Resolution Entity
     */
    public Resolution createEntity() {
        return Resolution.builder()
                .name(this.name)
                .description(this.description)
                .sortOrder(this.sortOrder)
                .build();
    }

    /**
     * constructor static method.
     * @param resolution Resolution Entity
     * @return created ResolutionDto
     */
    public static ResolutionDto from(Resolution resolution) {
        ResolutionDto dto = new ResolutionDto();
        dto.id = resolution.getId();
        dto.name = resolution.getName();
        dto.description = resolution.getDescription();
        dto.sortOrder = resolution.getSortOrder();

        return dto;
    }
}
