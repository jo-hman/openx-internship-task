package com.jochman;

import com.jochman.entities.appuser.AppUser;
import com.jochman.entities.cart.HighestValueCart;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("product-categories-value")
    public ResponseEntity<Map<String, Double>> getProductCategoriesValue(){
        return new ResponseEntity<>(shopService.getProductCategoriesTotalValue(), HttpStatus.OK);
    }

    @GetMapping("highest-value-cart")
    public ResponseEntity<HighestValueCart> getHighestValueCart(){
        return new ResponseEntity<>(shopService.getHighestValueCart(), HttpStatus.OK);
    }

    @GetMapping("two-furthest-users")
    public ResponseEntity<List<AppUser>> getTwoFurthestUsers(){
        return new ResponseEntity<>(shopService.getTwoFurthestFromEachOtherUsers(), HttpStatus.OK);
    }

}
