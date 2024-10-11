package myapp.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "cart")
public class Cart {
	
	 	@Id
	 	@Column(name = "menu_name")
	    private String menuName;//해당 순서에 맞추어서 데이터에서 검색함
	 	
	 	private String id;
	 	
	 	@Column(name = "menu_nameen")
	    private String menuNameen;
	 	
	 	private int count;
	 	
	    private int price;
	    
	    public Cart() {}
	    
	    public Cart(String id,String menuName,String menuNameen,int count,int price) {  //데이터를 받아서 넣을 값을 지정
	    	this.id = id;
	    	this.menuName = menuName;
	        this.menuNameen = menuNameen;
	        this.count = count;
	        this.price = price;
	    }

	    public String getMenuName() {
	        return menuName;
	    }

	    public void setMenuName(String menuName) {
	        this.menuName = menuName;
	    }
	    
	    public String getMenuNameen() {
	        return menuNameen;
	    }

	    public void setMenuNameen(String menuNameen) {
	        this.menuNameen = menuNameen;
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
