package myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders") // 테이블 이름
public class Order {
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // String 타입의 UUID 사용
    private String id;
    private String userId;    // 주문한 사용자 ID
    
    private String menuName;  // 주문한 메뉴 이름
    private int quantity;     // 수량
    private int price;        // 가격
    private String situation; // 현재상태

    // 기본 생성자
    public Order() {}

    // 생성자
    public Order(String menuName, String userId, int quantity, int price,String situation) {
        this.menuName = menuName;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.situation = situation;
    }

    // Getter, Setter
    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getsituation() {
        return situation;
    }

    public void setsituation(String situation) {
        this.situation = situation;
    }
}

