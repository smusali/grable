package org.esi.grable.restaurant_service.restaurants.controller;

import org.esi.grable.restaurant_service.restaurants.model.RestaurantCriteria;
import org.esi.grable.restaurant_service.restaurants.service.RestaurantService;
import org.esi.grable.restaurant_service.restaurants.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurants", description = "CRUD Operations for Restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("")
    public List<Restaurant> getAllRestaurants(@ModelAttribute RestaurantCriteria criteria) {
        return restaurantService.getAllRestaurants(criteria);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurant(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRestaurants(@RequestBody Restaurant restaurant) {
        restaurantService.addRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok().body("Restaurant updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().body("Restaurant deleted successfully.");
    }

}
