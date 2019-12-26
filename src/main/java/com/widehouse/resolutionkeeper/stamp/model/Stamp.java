package com.widehouse.resolutionkeeper.stamp.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "stamp")
public class Stamp {
    @Id
    private String id;
    private String resolutionId;

    @CreatedDate
    private Instant createdAt;
}
