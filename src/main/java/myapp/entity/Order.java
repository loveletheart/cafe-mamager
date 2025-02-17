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

    private String menuName;  // 주문한 메뉴 이름
    private String userId;    // 주문한 사용자 ID
    private int quantity;     // 수량
    private int price;        // 가격

    // 기본 생성자
    public Order() {}

    // 생성자
    public Order(String menuName, String userId, int quantity, int price) {
        this.menuName = menuName;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter, Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

