package com.jochman.entities.appuser;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    private String firstname;
    private String lastname;
}
