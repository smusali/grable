package org.esi.grable.restaurants.controller;

import org.esi.grable.restaurants.model.Restaurant;
import org.esi.grable.restaurants.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("")
    public List<Restaurant> getAllRestaurants(@RequestParam(required = false) String location,
                                              @RequestParam(required = false) String name) {
        return restaurantService.getAllRestaurants(location, name);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable String id) {
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
    public ResponseEntity<String> deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().body("Restaurant deleted successfully.");
    }

}
