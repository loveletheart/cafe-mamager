package myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserData {
	@Id
    private String id;
    private String username;
    private String password;

    // Getter 및 Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}