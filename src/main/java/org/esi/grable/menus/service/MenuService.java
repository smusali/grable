package org.esi.grable.menus.service;

import org.esi.grable.menus.model.Menu;
import org.esi.grable.menus.repository.MenuRepository;
import org.esi.grable.restaurants.model.Restaurant;
import org.esi.grable.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Menu createMenu(Long restaurantId, LocalDateTime lastUpdated) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Menu menu = new Menu();
            menu.setLastUpdated(lastUpdated);
            menu.setRestaurant(restaurantOptional.get());
            return menuRepository.save(menu);
        } else {
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
        }
    }

    public Menu getMenuByRestaurantId(Long restaurantId) {
        return menuRepository.findFirstByRestaurantId(restaurantId).orElse(null);
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public void deleteMenu(Long restaurantId, Long menuId) {
        Optional<Menu> menuOptional = menuRepository.findByIdAndRestaurantId(menuId, restaurantId);
        if (menuOptional.isPresent()) {
            menuRepository.deleteById(menuId);
        } else {
            throw new IllegalArgumentException("Menu with ID " + menuId + " not found for Restaurant " + restaurantId);
        }
    }
}
