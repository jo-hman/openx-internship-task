package com.jochman.entities.appuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Geolocation {
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("long")
    private Double longitude;
}
