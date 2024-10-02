package myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import myapp.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
