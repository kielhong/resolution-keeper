package com.widehouse.resolutionkeeper.resolution.model;

import com.widehouse.resolutionkeeper.resolution.dto.ResolutionDto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Document(collection = "resolution")
public class Resolution {
    @Id
    private String id;
    private String name;
    private String description;
    private int sortOrder;

    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    @Builder
    private static Resolution of(String id, String name, String description, int sortOrder) {
        Resolution resolution = new Resolution();
        resolution.id = id;
        resolution.name = name;
        resolution.description = description;
        resolution.sortOrder = sortOrder;

        return resolution;
    }

    public void update(ResolutionDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.sortOrder = dto.getSortOrder();
    }
}
