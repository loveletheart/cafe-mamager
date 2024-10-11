package myapp.service;

import java.util.Optional;

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
    
    public Page<Menu> getMenuByCategory(String category,int page, int size) {
    	PageRequest pageRequest = PageRequest.of(page, size);
        return menuRepository.findByPage(category, pageRequest);
    }
    
    public boolean addToCart(String menuName) {
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
}
