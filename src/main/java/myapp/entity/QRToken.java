package myapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr_tokens")
public class QRToken {

    @Id
    private String token;  // UUID 기반 QR 코드 토큰

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData user;  // UserData와 연결 (FK)

    private LocalDateTime createdAt;  // QR 코드 생성 시간

    // 기본 생성자
    public QRToken() {}

    // 생성자
    public QRToken(String token, UserData user) {
        this.token = token;
        this.user = user;
        this.createdAt = LocalDateTime.now(); // 생성 시간 저장
    }

    // Getter
    public String getToken() {
        return token;
    }

    public UserData getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
