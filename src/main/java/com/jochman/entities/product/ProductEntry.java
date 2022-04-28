package com.jochman.entities.product;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntry {
    private Integer productId;
    private Integer quantity;
}
