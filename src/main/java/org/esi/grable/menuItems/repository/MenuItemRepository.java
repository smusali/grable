package org.esi.grable.menuItems.repository;

import org.esi.grable.menuItems.model.MenuItem; // Import your custom MenuItem class
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
}
