package org.esi.grable.menu_service.menuItems.model;

import lombok.Data;

@Data
public class MenuItemCriteria {
    private String name;
    private String category;
    private Boolean availability;
}
