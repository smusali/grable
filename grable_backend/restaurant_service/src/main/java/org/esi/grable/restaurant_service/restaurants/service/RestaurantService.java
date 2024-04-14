package org.esi.grable.restaurant_service.restaurants.service;

import lombok.extern.slf4j.Slf4j;
import org.esi.grable.restaurant_service.restaurants.repository.RestaurantRepository;
import org.esi.grable.restaurant_service.restaurants.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants(String location, String name) {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurantRepository.findAll()
                .forEach(restaurant -> {
                    if ((isNull(location) || location.equals(restaurant.getLocation())) &&
                            (isNull(name) || name.equals(restaurant.getName()))) {
                        restaurants.add(restaurant);
                    }
                });
        return restaurants;
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        log.info("Restaurant {} is added to the Database", restaurant.getId());
    }

    public void updateRestaurant(Long id, Restaurant restaurant) {
        Optional<Restaurant> existingRestaurantOptional = restaurantRepository.findById(Long.valueOf(id));
        if (existingRestaurantOptional.isPresent()) {
            Restaurant existingRestaurant = Restaurant.builder()
                    .id(id)
                    .name(restaurant.getName())
                    .location(restaurant.getLocation())
                    .contactInfo(restaurant.getContactInfo())
                    .build();
            restaurantRepository.save(existingRestaurant);
            log.info("Restaurant {} is updated in the Database", existingRestaurant.getId());
        } else {
            String errorMessage = String.format("Restaurant with ID %s not found", id);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void deleteRestaurant(Long id) {
        if (restaurantRepository.findById(id).isPresent()) {
            restaurantRepository.deleteById(id);
            log.info("Restaurant {} is deleted from the Database", id);
        } else {
            String errorMessage = String.format("Restaurant with ID %d not found", id);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
