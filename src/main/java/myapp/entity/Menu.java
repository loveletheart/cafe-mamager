package myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    private String menu_Name;//해당 순서에 맞추어서 데이터에서 검색함
    private String type;
    private int price;

    // Getters and Setters
    public String getMenu() {
        return type;
    }

    public void setMenu(String type) {
        this.type = type;
    }

    public String getMenuName() {
        return menu_Name;
    }

    public void setMenuName(String menu_Name) {
        this.menu_Name = menu_Name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
