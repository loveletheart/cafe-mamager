package myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import myapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
