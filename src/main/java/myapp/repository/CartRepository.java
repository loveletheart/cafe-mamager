package myapp.repository;

import myapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    // 사용자 아이디와 메뉴 이름을 기준으로 장바구니 항목을 찾는 메서드
	List<Cart> findByUserId(String userId);
    // 사용자 아이디와 메뉴 이름을 기준으로 장바구니 항목을 하나만 찾는 메서드
	Optional<Cart> findByuserIdAndMenuName(String userId, String menuName);
	// 사용자 아이디에 맞는 저장된 데이터 삭제
	void deleteByUserId(String userId);
}
