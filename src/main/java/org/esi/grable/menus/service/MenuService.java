package org.esi.grable.menus.service;

import org.esi.grable.menus.model.Menu;
import org.esi.grable.menus.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(LocalDateTime lastUpdated) {
        Menu menu = new Menu();
        menu.setLastUpdated(lastUpdated);
        return menuRepository.save(menu);
    }

    public Menu getMenuById(Long id) {
        Optional<Menu> menuOptional = menuRepository.findById(id);
        return menuOptional.orElse(null);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}