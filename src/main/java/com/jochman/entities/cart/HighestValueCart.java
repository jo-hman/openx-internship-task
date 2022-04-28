package com.jochman.entities.cart;

import com.jochman.entities.appuser.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HighestValueCart {
    private Cart cart;
    private Double value;
    private Name ownerName;
}
