package myapp.repository;

import myapp.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, String> {
    Optional<UserData> findById(String id);
}