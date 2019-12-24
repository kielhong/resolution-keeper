package com.widehouse.resolutionkeeper.stamp.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

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
