package myapp.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "cart")
public class Cart {
	
	 	@Id
	 	private String id;
	    private String menu_Name;//해당 순서에 맞추어서 데이터에서 검색함
	    private String menu_Nameen;
	    private int count;
	    private int price;
	    
	    protected Cart() {}
	    
	    public Cart(String id,String menu_Name,String menu_Nameen,int count,int price) {  //데이터를 받아서 넣을 값을 지정
	    	this.id = id;
	    	this.menu_Name = menu_Name;
	        this.menu_Nameen = menu_Nameen;
	        this.count = count;
	        this.price = price;
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
	    
	    public int getCount() {
	        return count;
	    }

	    public void setCount(int count) {
	        this.count = count;
	    }
	    
	    public int getPrice() {
	        return price;
	    }

	    public void setPrice(int price) {
	        this.price = price;
	    }
}
