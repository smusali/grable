package org.esi.grable.menu_service.menuItems.service;

import org.esi.grable.menu_service.menuItems.model.MenuItem;
import org.esi.grable.menu_service.menuItems.model.MenuItemCriteria;
import org.esi.grable.menu_service.menuItems.repository.MenuItemRepository;
import org.esi.grable.menu_service.menus.model.Menu;
import org.esi.grable.menu_service.menus.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    public MenuItem createMenuItem(Long menuId, MenuItem menuItem) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            menuItem.setMenu(menu.get());
            return menuItemRepository.save(menuItem);
        } else {
            throw new IllegalArgumentException("Menu not found with ID: " + menuId);
        }
    }

    public MenuItem getMenuItemByIdAndMenuId(Long itemId, Long menuId) {
        return menuItemRepository.findByIdAndMenuId(itemId, menuId).orElse(null);
    }

    public List<MenuItem> getItemsByCriteria(Long menuId, MenuItemCriteria criteria) {
        return menuItemRepository.findByMenuId(menuId).stream()
                .filter(item -> (isNull(criteria.getName()) || item.getName().equalsIgnoreCase(criteria.getName())) &&
                        (isNull(criteria.getCategory()) || item.getCategory().equalsIgnoreCase(criteria.getCategory())) &&
                        (isNull(criteria.getAvailability()) || item.isAvailability() == criteria.getAvailability()))
                .collect(Collectors.toList());
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem, Long menuId) {
        Optional<MenuItem> existingMenuItem = menuItemRepository.findByIdAndMenuId(id, menuId);
        if (existingMenuItem.isPresent()) {
            MenuItem updatedItem = existingMenuItem.get();
            updatedItem.setName(menuItem.getName());
            updatedItem.setDescription(menuItem.getDescription());
            updatedItem.setPrice(menuItem.getPrice());
            updatedItem.setCategory(menuItem.getCategory());
            updatedItem.setAvailability(menuItem.isAvailability());
            updatedItem.setDietaryInfo(menuItem.getDietaryInfo());

            MenuItem savedItem = menuItemRepository.save(updatedItem);

            Menu menu = savedItem.getMenu();
            menu.setLastUpdated(LocalDateTime.now());
            menuRepository.save(menu);

            return savedItem;
        } else {
            throw new IllegalArgumentException("MenuItem with ID: " + id + " not found for Menu ID: " + menuId);
        }
    }

    public void deleteMenuItem(Long id, Long menuId) {
        Optional<MenuItem> menuItem = menuItemRepository.findByIdAndMenuId(id, menuId);
        if (menuItem.isPresent()) {
            menuItemRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Menu Item with ID " + id + " not found in the Menu " + menuId);
        }
    }
}