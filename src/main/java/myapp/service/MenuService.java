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

        // 현재 로그인한 사용자 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName(); // 현재 로그인한 사용자의 ID (username)

        // 해당 메뉴와 사용자 아이디로 장바구니 항목 찾기
        Cart existingCart = cartRepository.findByUserIdAndMenuName(id, menuName);

        if (existingCart != null) {
            existingCart.setCount(existingCart.getCount() + 1); // 이미 있으면 개수 증가
            cartRepository.save(existingCart);
        } else {
            Menu menu = menuItem.get();
            Cart cart = new Cart(id + menuName, id, menu.getMenuName(), menu.getMenuNameen(), 1, menu.getPrice());
            cartRepository.save(cart);
        }

        return true;
    }

    // 특정 사용자의 장바구니 목록 조회
    public List<Cart> getCartItemsByUser(String userId) {
        return cartRepository.findByUserId(userId);  // findByUserId로 수정
    }

    // 장바구니 수량 업데이트
    public boolean updateCartItem(String userId, String menuName, int count) {
        Cart existingCart = cartRepository.findByUserIdAndMenuName(userId, menuName);

        if (existingCart != null) {
            existingCart.setCount(count);
            cartRepository.save(existingCart);
            return true;
        }

        return false;
    }

    // 특정 사용자의 장바구니 아이템 가격 합 계산
    public int calculateItemPrice(String userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId); // 수정된 부분
        return cartItems.stream()
                .mapToInt(item -> item.getPrice() * item.getCount())
                .sum();
    }

    // 전체 장바구니 합계 계산 (사용자별로 수정 가능)
    public int calculateTotalSum(String userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);  // 수정된 부분
        return cartItems.stream().mapToInt(item -> item.getPrice() * item.getCount()).sum();
    }
}
