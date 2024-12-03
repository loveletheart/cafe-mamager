package myapp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import myapp.entity.*;
import myapp.repository.*;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CartRepository cartRepository;
    
    public Page<Menu> getMenuByCategory(String category,int page, int size) {//페이지 조회시 해당되는 데이터를 조회하고 데이터들이 개수를 세서 페이지를 몇개 보여주는 곳
    	PageRequest pageRequest = PageRequest.of(page, size);
        return menuRepository.findByPage(category, pageRequest);
    }
    
    public boolean addToCart(String menuName) {//장바구니에 담기 버튼 클릭시 해당 데이터 중복는되는지 확인하고 중복되면 +1해주고 중복이 안된다면 새로 저장
    	Optional<Menu> menuItem = menuRepository.findById(menuName);
    	Cart existingCart = cartRepository.findByMenuName(menuName);
    	
    	if (existingCart != null && menuItem.isPresent()) {
    		
    		existingCart.setCount(existingCart.getCount() + 1);
            cartRepository.save(existingCart);
            return true;
    	}else if(existingCart == null && menuItem.isPresent()) {
            Menu menu = menuItem.get();
            String id="1";

            // Cart 엔티티 생성 후 저장
            Cart cart =new Cart(id,menu.getMenuName(),menu.getMenuNameen(),1,menu.getPrice());  // 테이블id,메뉴 이름,메뉴 영어이름,메뉴개수,메뉴 가격
            cartRepository.save(cart);

            return true;  // 저장 성공 시 true 반환
        } else return false;  // Menu 테이블에서 해당 메뉴를 찾지 못한 경우
    }
    
    // 모든 장바구니 항목을 가져오는 메서드
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }
    
    
}
