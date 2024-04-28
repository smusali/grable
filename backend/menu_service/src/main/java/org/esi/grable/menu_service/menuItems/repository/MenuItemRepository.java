package org.esi.grable.menu_service.menuItems.repository;

import org.esi.grable.menu_service.menuItems.model.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
    Optional<MenuItem> findByIdAndMenuId(Long id, Long menuId);
    List<MenuItem> findByMenuId(Long menuId);
}