package com.widehouse.resolutionkeeper.stamp.dto;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;

import java.time.LocalDate;
import java.time.ZoneId;

import lombok.Getter;

@Getter
public class StampDto {
    private String id;
    private String resolutionId;
    private LocalDate stampDate;

    /**
     * constructor static method.
     * @param stamp Stamp Entity
     * @param zoneId base ZoneId
     */
    public static StampDto from(Stamp stamp, ZoneId zoneId) {
        StampDto dto = new StampDto();
        dto.id = stamp.getId();
        dto.resolutionId = stamp.getResolutionId();
        dto.stampDate = stamp.getCreatedAt().atZone(zoneId).toLocalDate();
        return dto;
    }
}
