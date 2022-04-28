Task was solved using Maven and Java 17 with Spring Boot, JUnit and Lombok (to get rid of boilerplate code).
I decided to create a RESTful application consuming RESTful API of "fakestoreapi.com".

Application retrieves data using RestTemplate and gives you following endpoints to hit:
localhost:8080/api/v1/shop
- /product-categories-value (returns representation of product categories with their total value)
- /highest-value-cart (returns representation of a cart with the highest value along with its total value and owner name)
- /two-furthest-users (returns two users that are furthest from each other)

I decided to retrieve all data at once and store all of it. It's not good approach in a real situation but for purpose of simplicity and because of the fact that it's small application with little amount of data I decided to do so.

Method in ShopService returning two furthest from each other users could have had a better complexity. I decided to implement a naive approach which has a complexity of O(n^2) which is not great for large datasets but implementing something with better complexity seemed to me as too much of a hustle for such a small application with little amount of data to digest.

I have also written tests that I considered necessary using JUnit implementation in spring-boot-starter-test dependency. 