package myapp.repository;

import myapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    // 사용자 아이디와 메뉴 이름을 기준으로 장바구니 항목을 찾는 메서드
	List<Cart> findByUserId(String userId);
    // 사용자 아이디와 메뉴 이름을 기준으로 장바구니 항목을 하나만 찾는 메서드
    Cart findByuserIdAndMenuName(String userId, String menuName);
}
