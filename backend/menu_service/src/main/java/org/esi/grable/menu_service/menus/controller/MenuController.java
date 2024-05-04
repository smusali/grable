package org.esi.grable.menu_service.menus.controller;

import org.esi.grable.menu_service.menus.model.Menu;
import org.esi.grable.menu_service.menus.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu")
@Tag(name = "Menus", description = "CRUD Operations for Menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Menu createMenu(@PathVariable Long restaurantId) {
        return menuService.createMenu(restaurantId, LocalDateTime.now());
    }

    @GetMapping
    public Menu getMenuByRestaurantId(@PathVariable Long restaurantId) {
        return menuService.getMenuByRestaurantId(restaurantId);
    }

    @GetMapping("/{menuId}")
    public Menu getMenuById(@PathVariable Long menuId) {
        return menuService.getMenuById(menuId);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long restaurantId, @PathVariable Long menuId) {
        menuService.deleteMenu(restaurantId, menuId);
        return ResponseEntity.ok().body("Menu deleted successfully.");
    }
}
