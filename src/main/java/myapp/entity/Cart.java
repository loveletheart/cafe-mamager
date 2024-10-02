package myapp.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "cart")
public class Cart {
	
	 	@Id
	    private String menu_Name;//해당 순서에 맞추어서 데이터에서 검색함
	    private String menu_Nameen;
	    private String type;
	    private int price;
	    private int quantity;
	    
	    public Cart(String menu_Name, int quantity, int price) {  // price는 double
	        this.menu_Name = menu_Name;
	        this.quantity = quantity;
	        this.price = price;
	    }

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
	    
	    public String getMenuNameen() {
	        return menu_Nameen;
	    }

	    public void setMenuNameen(String menu_Nameen) {
	        this.menu_Nameen = menu_Nameen;
	    }

	    public int getPrice() {
	        return price;
	    }

	    public void setPrice(int price) {
	        this.price = price;
	    }
	    
	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }
}
