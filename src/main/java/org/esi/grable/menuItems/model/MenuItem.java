package org.esi.grable.menuItems.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.esi.grable.menus.model.Menu;

@Setter
@Getter
@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String category;
    private boolean availability;
    private String dietaryInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
