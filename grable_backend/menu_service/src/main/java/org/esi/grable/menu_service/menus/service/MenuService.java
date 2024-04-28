package org.esi.grable.menu_service.menus.service;

import org.esi.grable.menu_service.menus.model.Menu;
import org.esi.grable.menu_service.menus.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Long restaurantId, LocalDateTime lastUpdated) {
        Menu menu = new Menu();
        menu.setLastUpdated(lastUpdated);
        menu.setRestaurantId(restaurantId);
        return menuRepository.save(menu);
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
