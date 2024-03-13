package org.esi.grable.menus.repository;

import org.esi.grable.menus.model.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Long> {
}