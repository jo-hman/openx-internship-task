package com.jochman.entities.product;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Double rate;
    private Long count;
}
