package myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import myapp.entity.*;
import myapp.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CartRepository cartRepository;

    // 카테고리별 메뉴 페이지 조회
    public Page<Menu> getMenuByCategory(String category, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return menuRepository.findByPage(category, pageRequest);
    }

    // 장바구니에 메뉴 추가
    public boolean addToCart(String menuName) {
        Optional<Menu> menuItem = menuRepository.findById(menuName);
        if (!menuItem.isPresent()) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 현재 로그인한 사용자의 ID

        List<Cart> existingCart = cartRepository.findByUserId(userId);  // userId로 장바구니 항목 찾기
        Optional<Cart> cart = existingCart.stream()
                                          .filter(c -> c.getMenuName().equals(menuName))
                                          .findFirst();

        if (cart.isPresent()) {
            cart.get().setCount(cart.get().getCount() + 1); // 이미 있으면 개수 증가
            cartRepository.save(cart.get());
        } else {
            Menu menu = menuItem.get();
            Cart newCart = new Cart(userId, menu.getMenuName(), menu.getMenuNameen(), 1, menu.getPrice());//없으면 새로 추가
            cartRepository.save(newCart);
        }

        return true;
    }
    
    //사용자의 아이디에 따라서 데이터베이스cart에 있는 데이터를 다르게 가지고 옴
    public List<Cart> getCartItemsByUser(String userId) {
        return cartRepository.findByUserId(userId);  // userId로 장바구니 항목 조회
    }
    
    //장바구니에 있는 개수 조절시 실행되는 api
    public boolean updateCartItem(String userId, String menuName, int count) {
        Optional<Cart> existingCart = cartRepository.findByuserIdAndMenuName(userId, menuName);

        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setCount(count);
            cartRepository.save(cart);
            return true;
        }

        return false;
    }
    
    // 전체 장바구니 총합 계산 메서드
    public int calculateTotalSum(String userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream()
                        .mapToInt(cart -> cart.getPrice() * cart.getCount())
                        .sum();
    }
    
    // 메뉴 가격 가져오는 메서드
    public int getPrice(String userId,String menuName) {
        Optional<Cart> menuData = cartRepository.findByuserIdAndMenuName(userId, menuName);
        return menuData.map(Cart::getPrice).orElse(0);
    }
}
