package org.esi.grable.menuItems.service;

import org.esi.grable.menuItems.model.MenuItem; // Import your custom MenuItem class
import org.esi.grable.menuItems.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemById(Long id) {
        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
        return menuItemOptional.orElse(null);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        // Implement update logic here if needed
        return null;
    }
}
