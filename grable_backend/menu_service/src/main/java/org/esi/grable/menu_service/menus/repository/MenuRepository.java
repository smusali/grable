package org.esi.grable.menu_service.menus.repository;

import org.esi.grable.menu_service.menus.model.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MenuRepository extends CrudRepository<Menu, Long> {
    Optional<Menu> findFirstByRestaurantId(Long restaurantId);
    Optional<Menu> findByIdAndRestaurantId(Long id, Long restaurantId);
}