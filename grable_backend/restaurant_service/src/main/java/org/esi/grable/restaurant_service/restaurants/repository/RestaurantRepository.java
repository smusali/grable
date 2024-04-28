package org.esi.grable.restaurant_service.restaurants.repository;

import org.esi.grable.restaurant_service.restaurants.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
