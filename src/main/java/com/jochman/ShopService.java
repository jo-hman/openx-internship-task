package com.jochman;

import com.jochman.entities.appuser.AppUser;
import com.jochman.entities.appuser.Geolocation;
import com.jochman.entities.appuser.Name;
import com.jochman.entities.cart.Cart;
import com.jochman.entities.cart.HighestValueCart;
import com.jochman.entities.product.Product;
import com.jochman.entities.product.ProductEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.lang.Math.*;

@Service
@AllArgsConstructor
public class ShopService {
    private List<AppUser> users;
    private List<Cart> carts;
    private List<Product> products;

    public ShopService(){
        //in real situation approach of retrieving all data at once and storing it wouldn't be a great idea
        //but since it's a simple application with little amount of data
        //I decided to do so for purpose of simplicity
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<AppUser[]> responseUsers = restTemplate.getForEntity("https://fakestoreapi.com/users", AppUser[].class);
        users = new ArrayList<>(Arrays.stream(responseUsers.getBody()).toList());

        ResponseEntity<Cart[]> responseCart = restTemplate.getForEntity("https://fakestoreapi.com/carts", Cart[].class);
        carts = new ArrayList<>(Arrays.stream(responseCart.getBody()).toList());

        ResponseEntity<Product[]> responseProducts = restTemplate.getForEntity("https://fakestoreapi.com/products", Product[].class);
        products = new ArrayList<>(Arrays.stream(responseProducts.getBody()).toList());
    }

    public Map<String, Double> getProductCategoriesTotalValue() {
        Map<String, Double> productCategoriesValue = new HashMap<>();

        for(Product product : products){
            String category = product.getCategory();

            if(!productCategoriesValue.containsKey(category)){
                productCategoriesValue.put(category, product.getPrice());
            } else {
                productCategoriesValue.put(category, productCategoriesValue.get(category) + product.getPrice());
            }
        }

        return productCategoriesValue;
    }

    public HighestValueCart getHighestValueCart() {

        double maxValue = 0;
        Cart highestValueCart = carts.get(0);

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

        Name name = users.get(highestValueCart.getUserId() - 1).getName();

        return new HighestValueCart(highestValueCart, maxValue, name);
    }

    public List<AppUser> getTwoFurthestUsers() {
        double maxDistance = 0;
        AppUser maxDistanceUser1 = users.get(0);
        AppUser maxDistanceUser2 = users.get(0);

        for(AppUser user1 : users){
            for(AppUser user2 : users){
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
        }

        List<AppUser> result = new LinkedList<>();
        result.add(maxDistanceUser1);
        result.add(maxDistanceUser2);

        return result;
    }
}
