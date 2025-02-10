package myapp.controller;

import myapp.entity.*;
import myapp.service.MenuService;
import myapp.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import java.util.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    private CartRepository cartRepository;

    /**
     * 기본 메뉴 페이지 (GET 요청)
     * 사용자가 `/menu`로 접근하면 기본적으로 'coffee' 카테고리를 보여줌
     */
    @GetMapping
    public ModelAndView showDefaultMenu(@RequestParam(defaultValue = "0") int page) {
    	 // 리다이렉트 URL에 페이지 파라미터 포함
        return new ModelAndView("redirect:/menu/coffee?page=" + page);
    }

    /**
     * 특정 카테고리 메뉴 페이지 (GET 요청)
     * 사용자가 `/menu/{category}`로 접근하면 해당 카테고리의 메뉴를 조회함
     * 예: `/menu/aid`, `/menu/cookie`
     */
    @GetMapping("/{category}")
    public ModelAndView getMenuByCategory(@PathVariable String category, @RequestParam(defaultValue = "0") int page) {
        return getMenu(category, page);  // 해당 카테고리의 메뉴를 조회
    }

    /**
     * 내부 메서드 - 메뉴 데이터 조회 및 페이지 반환
     * 카테고리에 따라 데이터를 가져와서 ModelAndView를 구성하여 반환
     */
    private ModelAndView getMenu(String category, int page) {
        int pageSize = 12;  // 한 페이지당 12개의 아이템을 표시
        Page<Menu> menuPage = menuService.getMenuByCategory(category, page, pageSize);

        String viewName = "menu/" + category; // 뷰 이름 지정 (ex: menu/coffee, menu/aid)
        ModelAndView modelAndView = new ModelAndView(viewName);

        modelAndView.addObject("menus", menuPage.getContent());  // 메뉴 데이터 추가
        modelAndView.addObject("activeCategory", category);  // 현재 카테고리 지정 (버튼 활성화용)
        modelAndView.addObject("totalPages", menuPage.getTotalPages());  // 전체 페이지 수 추가
        modelAndView.addObject("currentPage", page);  // 현재 페이지 번호 추가

        System.out.println("Menus on current page: " + menuPage.getContent().size()); // 디버깅용 출력

        return modelAndView;
    }

    /**
     * 장바구니 추가 (AJAX 요청 처리)
     * 사용자가 '장바구니 추가' 버튼을 클릭하면 호출됨
     */
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, String> request) {
        String menuName = request.get("menuName");

        boolean result = menuService.addToCart(menuName);

        if (result) {
            return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
        } else {
            return ResponseEntity.status(500).body("장바구니 추가 실패");
        }
    }

    /**
     * 로그인한 사용자의 장바구니 목록 조회
     */
    @GetMapping("/cart")
    public String showCart(@RequestParam String userId, Model model) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);  // userId로 장바구니 아이템을 조회
        model.addAttribute("cartItems", cartItems);
        return "menu/cart";  // 장바구니 페이지로 리턴
    }

    /**
     * ✅ 장바구니 상품 수량 업데이트
     */
    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCartItem(@RequestParam String userId, @RequestParam String menuName, @RequestParam int count) {
        Optional<Cart> cartItemOptional = cartRepository.findById(userId + menuName);

        if (cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            cartItem.setCount(count);
            cartRepository.save(cartItem);
            return "장바구니가 업데이트 되었습니다.";
        } else {
            return "장바구니에 해당 아이템이 없습니다.";
        }
    }
}