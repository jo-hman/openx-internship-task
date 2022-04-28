package com.jochman.entities.cart;

import com.jochman.entities.product.ProductEntry;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart {
    private Integer id;
    private Integer userId;
    private String date;
    private List<ProductEntry> products;
}
