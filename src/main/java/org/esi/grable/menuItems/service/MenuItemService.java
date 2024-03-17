package org.esi.grable.menuItems.service;

import org.esi.grable.menuItems.model.MenuItem;
import org.esi.grable.menuItems.repository.MenuItemRepository;
import org.esi.grable.menus.model.Menu;
import org.esi.grable.menus.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<MenuItem> getItemsByCriteria(Long menuId, String name, String category, Boolean availability) {
        Set<MenuItem> filteredItemsSet = new HashSet<>();
        menuItemRepository.findByMenuId(menuId).forEach(item -> {
            boolean matchesName = (name == null || item.getName().equalsIgnoreCase(name));
            boolean matchesCategory = (category == null || item.getCategory().equalsIgnoreCase(category));
            boolean matchesAvailability = (availability == null || item.isAvailability() == availability);

            if (matchesName && matchesCategory && matchesAvailability) {
                filteredItemsSet.add(item);
            }
        });
        return new ArrayList<>(filteredItemsSet);
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