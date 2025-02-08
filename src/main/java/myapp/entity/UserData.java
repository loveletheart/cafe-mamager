package myapp.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "userdata")
public class UserData implements UserDetails {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String username;
    private String password;
    private String role;  // Role을 소문자 'role'로 변경

    // 기본 생성자
    public UserData() {}

    // 파라미터를 받는 생성자
    public UserData(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;  // 'Role' -> 'role'로 수정
    }
    
    // UserDetails 메서드 구현

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // role을 권한으로 변환
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));  // role을 권한으로 추가
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id; // id를 username으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getter 및 Setter 추가

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;  // role 반환
    }

    public void setRole(String role) {
        this.role = role;  // role 설정
    }
}
