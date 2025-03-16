package myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "qr_tokens")
public class QRToken {

    @Id
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private String userId;

    // 기본 생성자 (JPA용)
    public QRToken() {}

    // 추가: (String token, String userId) 생성자
    public QRToken(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    // Getter & Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
