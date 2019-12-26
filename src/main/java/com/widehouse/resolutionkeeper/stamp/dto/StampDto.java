package com.widehouse.resolutionkeeper.stamp.dto;

import com.widehouse.resolutionkeeper.stamp.model.Stamp;

import java.time.LocalDate;
import java.time.ZoneId;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StampDto {
    private String id;
    private String resolutionId;
    private LocalDate createdDate;

    public static StampDto from(Stamp stamp) {
        StampDto dto = new StampDto();
        dto.id = stamp.getId();
        dto.resolutionId = stamp.getResolutionId();
        dto.createdDate = stamp.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
        return dto;
    }
}
