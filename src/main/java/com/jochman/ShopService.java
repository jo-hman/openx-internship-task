package com.jochman;

import com.jochman.entities.appuser.AppUser;
import com.jochman.entities.appuser.Geolocation;
import com.jochman.entities.appuser.Name;
import com.jochman.entities.cart.Cart;
import com.jochman.entities.cart.HighestValueCart;
import com.jochman.entities.product.Product;
import com.jochman.entities.product.ProductEntry;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.lang.Math.*;

@Service
@AllArgsConstructor
public class ShopService {

    private final List<AppUser> users;
    private final List<Cart> carts;
    private final List<Product> products;

    /**
     * ShopService constructor consuming https://fakestoreapi.com REST API
     */
    public ShopService(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<AppUser[]> responseUsers =
                restTemplate.getForEntity("https://fakestoreapi.com/users", AppUser[].class);
        users = new ArrayList<>(
                Arrays.stream(Objects.requireNonNull(responseUsers.getBody())).toList()
        );

        ResponseEntity<Cart[]> responseCart =
                restTemplate.getForEntity("https://fakestoreapi.com/carts", Cart[].class);
        carts = new ArrayList<>(
                Arrays.stream(Objects.requireNonNull(responseCart.getBody())).toList()
        );

        ResponseEntity<Product[]> responseProducts =
                restTemplate.getForEntity("https://fakestoreapi.com/products", Product[].class);
        products = new ArrayList<>(
                Arrays.stream(Objects.requireNonNull(responseProducts.getBody())).toList()
        );
    }

    /**
     * finds all available product categories and calculates value of all products from a category
     * @return HashMap with categories as keys and total category value as values
     */
    public Map<String, Double> getProductCategoriesTotalValue() {
        Map<String, Double> productCategoriesValue = new HashMap<>();

        for(Product product : products){
            String category = product.getCategory();

            if(!productCategoriesValue.containsKey(category)){
                productCategoriesValue.put(category,
                        product.getPrice());
            } else {
                productCategoriesValue.put(category,
                        productCategoriesValue.get(category) + product.getPrice());
            }
        }

        return productCategoriesValue;
    }

    /**
     * finds cart with the highest value of products in it
     * @return HighestValueCart representing Cart along with total value of its products and owner name
     */
    public HighestValueCart getHighestValueCart() {

        double maxValue = 0;
        Cart highestValueCart = null;

        for(Cart cart : carts) {
            double cartValue = 0;

            for (ProductEntry productEntry : cart.getProducts()) {
                cartValue += products.get(productEntry.getProductId() - 1).getPrice() * productEntry.getQuantity();
            }
            if(maxValue < cartValue){
                maxValue = cartValue;
                highestValueCart = cart;
            }
        }

        Name name = null;
        if (highestValueCart != null) {
            name = users.get(highestValueCart.getUserId() - 1).getName();
        }

        return new HighestValueCart(highestValueCart, maxValue, name);
    }

    /**
     * finds two furthest from each other users
     * @return List consisting of two furthest from each other users
     */
    public List<AppUser> getTwoFurthestFromEachOtherUsers() {
        double maxDistance = 0;
        AppUser maxDistanceUser1 = null;
        AppUser maxDistanceUser2 = null;

        List<AppUser> tempUsers = new LinkedList<>(users);

        for(AppUser user1 : users){
            for(AppUser user2 : tempUsers){
                Geolocation geolocation1 = user1.getAddress().getGeolocation();
                Geolocation geolocation2 = user2.getAddress().getGeolocation();

                double distance = sqrt(pow(geolocation1.getLatitude() - geolocation2.getLatitude(), 2) +
                        pow(geolocation1.getLongitude() - geolocation2.getLongitude(), 2));

                if(maxDistance < distance){
                    maxDistance = distance;
                    maxDistanceUser1 = user1;
                    maxDistanceUser2 = user2;
                }
            }
            tempUsers.remove(0); //removes already checked Geolocation
        }

        return new ArrayList<>(
                Arrays.asList(maxDistanceUser1, maxDistanceUser2));
    }
}
