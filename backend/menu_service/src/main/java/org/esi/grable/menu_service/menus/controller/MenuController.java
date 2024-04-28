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
    public ResponseEntity<Menu> createMenu(@PathVariable Long restaurantId) {
        Menu menu = menuService.createMenu(restaurantId, LocalDateTime.now());
        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Menu> getMenuByRestaurantId(@PathVariable Long restaurantId) {
        Menu menu = menuService.getMenuByRestaurantId(restaurantId);
        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long menuId) {
        Menu menu = menuService.getMenuById(menuId);
        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long restaurantId, @PathVariable Long menuId) {
        menuService.deleteMenu(restaurantId, menuId);
        return ResponseEntity.ok().body("Menu deleted successfully.");
    }
}
