package org.esi.grable.menuItems.controller;

import org.esi.grable.menuItems.model.MenuItem;
import org.esi.grable.menuItems.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu/{menuId}/items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@PathVariable Long menuId, @RequestBody MenuItem menuItem) {
        MenuItem createdMenuItem = menuItemService.createMenuItem(menuId, menuItem);
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long menuId, @PathVariable Long itemId) {
        MenuItem menuItem = menuItemService.getMenuItemByIdAndMenuId(itemId, menuId);
        if (menuItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getItemsByCriteria(@PathVariable Long menuId,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String category,
                                                             @RequestParam(required = false) Boolean availability) {
        List<MenuItem> items = menuItemService.getItemsByCriteria(menuId, name, category, availability);
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long menuId, @PathVariable Long itemId, @RequestBody MenuItem menuItem) {
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(itemId, menuItem, menuId);
        if (updatedMenuItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId, @PathVariable Long itemId) {
        menuItemService.deleteMenuItem(itemId, menuId);
        return ResponseEntity.ok().body("Menu item deleted successfully.");
    }
}