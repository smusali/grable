package org.esi.grable.menu_service.menus.service;

import org.esi.grable.menu_service.menus.model.Menu;
import org.esi.grable.menu_service.menus.repository.MenuRepository;
import org.esi.grable.restaurant_service.restaurants.model.Restaurant;
import org.esi.grable.restaurant_service.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Menu createMenu(Long restaurantId, LocalDateTime lastUpdated) {
        Restaurant restaurant = webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8080/restaurants/{restaurantId}", restaurantId)
                .retrieve()
                .bodyToMono(Restaurant.class)
                .block();

        if (nonNull(restaurant)) {
            Menu menu = new Menu();
            menu.setLastUpdated(lastUpdated);
            menu.setRestaurantId(restaurant.getId());
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
