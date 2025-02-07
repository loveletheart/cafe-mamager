package myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userdata")
public class UserData {
	@Id
    private String id;
    private String username;
    private String password;
    private String Role;

    // Getter 및 Setter
    
    public UserData() {
    }

    public UserData(String id,String username,String password,String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.Role= role;
    }

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
    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
}