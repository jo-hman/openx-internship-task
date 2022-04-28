package com.jochman.entities.appuser;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Geolocation geolocation;
    private String city;
    private String street;
    private Integer number;
    private String zipcode;
}
