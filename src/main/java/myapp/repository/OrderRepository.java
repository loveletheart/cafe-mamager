package myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    // 기본적인 CRUD 메서드를 제공
}
