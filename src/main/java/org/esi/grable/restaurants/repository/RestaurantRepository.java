package org.esi.grable.restaurants.repository;

import org.esi.grable.restaurants.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
