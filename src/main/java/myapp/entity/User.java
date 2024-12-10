package myapp.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
    
    public Long getid() {
        return id;
    }
    public void setid(Long id) {
        this.id = id;
    }
    
    public String getusername() {
        return username;
    }
    public void setusername(String username) {
        this.username = username;
    }
    
    public String getpassword() {
        return password;
    }
    public void setpassword(String password) {
        this.password = password;
    }
    
    public String getrole() {
        return role;
    }
    public void setrole(String role) {
        this.role = role;
    }
}
