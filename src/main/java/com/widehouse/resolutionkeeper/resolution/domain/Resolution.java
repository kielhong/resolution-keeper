package com.widehouse.resolutionkeeper.resolution.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
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
}
