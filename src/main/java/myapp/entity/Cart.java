package myapp.entity;

import jakarta.persistence.*;

@Entity
public class Cart {

    @Id
    private String id;  // 복합키 또는 간단한 id로 사용

    private String userId;
    private String menuName;
    private String menuNameen;
    private int count;
    private int price;

    // 기본 생성자
    public Cart() {}

    // 모든 필드를 포함한 생성자
    public Cart(String id, String userId, String menuName, String menuNameen, int count, int price) {
        this.id = id;
        this.userId = userId;
        this.menuName = menuName;
        this.menuNameen = menuNameen;
        this.count = count;
        this.price = price;
    }

    // 게터 및 세터 메서드들
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
