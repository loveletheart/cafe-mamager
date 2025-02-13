package myapp.repository;

import myapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    // 사용자 아이디와 메뉴 이름을 기준으로 장바구니 항목을 찾는 메서드
    Optional<Cart> findById(String id);

    // 사용자 아이디와 메뉴 이름을 기준으로 장바구니 항목을 하나만 찾는 메서드
    Cart findByIdAndMenuName(String id, String menuName);
}
