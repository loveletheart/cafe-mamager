package myapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
	Cart findByMenuName(String Cart);// 특정 메뉴가 이미 장바구니에 있는지 확인
}
