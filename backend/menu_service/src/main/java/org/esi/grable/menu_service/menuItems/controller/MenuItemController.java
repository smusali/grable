package org.esi.grable.menu_service.menuItems.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esi.grable.menu_service.menuItems.model.MenuItem;
import org.esi.grable.menu_service.menuItems.model.MenuItemCriteria;
import org.esi.grable.menu_service.menuItems.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu/{menuId}/items")
@Tag(name = "Menu Items", description = "CRUD Operations for Menu Items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem createMenuItem(@PathVariable Long menuId, @RequestBody MenuItem menuItem) {
        return menuItemService.createMenuItem(menuId, menuItem);
    }

    @GetMapping("/{itemId}")
    public MenuItem getMenuItemById(@PathVariable Long menuId, @PathVariable Long itemId) {
        return menuItemService.getMenuItemByIdAndMenuId(itemId, menuId);
    }

    @GetMapping
    public List<MenuItem> getItemsByCriteria(@PathVariable Long menuId,
                                             @ModelAttribute MenuItemCriteria criteria) {
        return menuItemService.getItemsByCriteria(menuId, criteria);
    }

    @PutMapping("/{itemId}")
    public MenuItem updateMenuItem(@PathVariable Long menuId, @PathVariable Long itemId, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(itemId, menuItem, menuId);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId, @PathVariable Long itemId) {
        menuItemService.deleteMenuItem(itemId, menuId);
        return ResponseEntity.ok().body("Menu item deleted successfully.");
    }
}