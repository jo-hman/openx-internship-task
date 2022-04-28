package com.jochman;

import com.jochman.entities.appuser.Address;
import com.jochman.entities.appuser.AppUser;
import com.jochman.entities.appuser.Geolocation;
import com.jochman.entities.appuser.Name;
import com.jochman.entities.cart.Cart;
import com.jochman.entities.cart.HighestValueCart;
import com.jochman.entities.product.Product;
import com.jochman.entities.product.ProductEntry;
import com.jochman.entities.product.Rating;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopServiceTest {

    @Test
    void shouldReturnProductCategoriesWithCorrectTotalValue(){
        //Given
        List<Product> products = generateProductsExample();

        ShopService shopService = new ShopService(new ArrayList<>(), new ArrayList<>(), products);

        Map<String, Double> expectedResult = new HashMap<>();
        expectedResult.put(products.get(0).getCategory(),
                products.get(0).getPrice());
        expectedResult.put(products.get(1).getCategory(),
                products.get(0).getPrice() + products.get(1).getPrice());
        expectedResult.put(products.get(2).getCategory(),
                products.get(2).getPrice());

        //When
        Map<String, Double> productCategoriesTotalValues = shopService.getProductCategoriesTotalValue();

        //Then
        assertEquals(expectedResult, productCategoriesTotalValues);
    }

    @Test
    void shouldReturnCartWithHighestValue(){
        //Given
        List<AppUser> users = generateUsersExample();
        List<Product> products = generateProductsExample();
        List<Cart> carts = generateCartsExample();

        ShopService shopService = new ShopService(users, carts, products);

        HighestValueCart expectedResult = new HighestValueCart(carts.get(0),
                480.0,
                users.get(0).getName());

        //When
        HighestValueCart highestValueCart = shopService.getHighestValueCart();

        //Then
        assertEquals(expectedResult.getCart(), highestValueCart.getCart());
        assertEquals(expectedResult.getValue(), highestValueCart.getValue());
        assertEquals(expectedResult.getOwnerName(), highestValueCart.getOwnerName());
    }



    @Test
    void shouldReturnTwoFurthestFromEachOtherUsers(){
        //Given
        List<AppUser> users = generateUsersExample();

        ShopService shopService = new ShopService(users, new ArrayList<>(), new ArrayList<>());

        List<AppUser> expectedResult = new LinkedList<>();
        expectedResult.add(users.get(0));
        expectedResult.add(users.get(2));

        //When
        List<AppUser> twoFurthestFromEachOtherUsers = shopService.getTwoFurthestUsers();

        //Then
        assertEquals(expectedResult, twoFurthestFromEachOtherUsers);
    }

    private List<Product> generateProductsExample(){
        List<Product> products = new LinkedList<>();
        products.add(new Product(1,
                "title",
                100.0,
                "description",
                "electronics",
                "image",
                new Rating()));
        products.add(new Product(2,
                "title",
                56.0,
                "description",
                "electronics",
                "image",
                new Rating()));
        products.add(new Product(3,
                "title",
                13.0,
                "description",
                "clothing",
                "image",
                new Rating()));

        return products;
    }

    private List<Cart> generateCartsExample() {
        List<Cart> carts = new LinkedList<>();
        carts.add(new Cart(1,
                1,
                "date",
                Arrays.asList(new ProductEntry(1, 2), new ProductEntry(2, 5))));
        carts.add(new Cart(2,
                2,
                "date",
                Arrays.asList(new ProductEntry(2, 1), new ProductEntry(3, 2))));

        return carts;
    }

    private List<AppUser> generateUsersExample(){
        List<AppUser> users = new LinkedList<>();
        users.add(new AppUser(new Address(
                new Geolocation(95.0, 85.0),
                "city",
                "street",
                123,
                "zipcode"
        ),
                1,
                "email",
                "password",
                new Name("Jacek", "Wariat"),
                "phone",
                0L));
        users.add(new AppUser(new Address(
                new Geolocation(0.0, 0.0),
                "city",
                "street",
                123,
                "zipcode"
        ),
                2,
                "email",
                "password",
                new Name("Jacek", "Bigoslaw"),
                "phone",
                0L));
        users.add(new AppUser(new Address(
                new Geolocation(-95.0, -85.0),
                "city",
                "street",
                123,
                "zipcode"
        ),
                3,
                "email",
                "password",
                new Name("Marek", "Bogdan"),
                "phone",
                0L));

        return users;
    }
}
