package myapp.controller;

import myapp.entity.*;
import myapp.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import java.util.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 기본 메뉴 페이지 (GET 요청)
     * 사용자가 `/menu`로 접근하면 기본적으로 'coffee' 카테고리를 보여줌
     */
    @GetMapping
    public ModelAndView showDefaultMenu(@RequestParam(defaultValue = "0") int page) {
        return getMenu("coffee", page);  // 기본적으로 커피 카테고리를 보여줌
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
    public ResponseEntity<String> addToCart(@RequestBody Cart cart) {
        boolean result = menuService.addToCart(cart.getMenuName());  // 장바구니 추가 로직 실행

        if (result) {
            return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
        } else {
            return ResponseEntity.status(500).body("장바구니 추가에 실패했습니다.");
        }
    }

    /**
     * 장바구니 페이지 이동 (GET 요청)
     * 사용자가 `/menu/cart`로 접근하면 장바구니 페이지를 보여줌
     */
    @GetMapping("/cart")
    public ModelAndView showCart() {
        List<Cart> cartItems = menuService.getAllCartItems();  // 장바구니 데이터 가져오기
        ModelAndView mav = new ModelAndView("menu/cart");  // 뷰 이름: cart.html
        mav.addObject("cartItems", cartItems);  // 장바구니 데이터 추가
        return mav;
    }

    /**
     * 장바구니 수량 업데이트 (AJAX 요청 처리)
     * 사용자가 장바구니에서 수량을 변경하면 서버에서 업데이트 후 총 가격을 반환
     */
    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCartItem(@RequestBody Map<String, Object> request) {
        String id = (String) request.get("id");  // 장바구니 아이템 ID 가져오기
        int count = (int) request.get("count");  // 변경된 수량 가져오기

        boolean success = menuService.updateItemCount(id, count);  // 수량 업데이트
        Map<String, Object> response = new HashMap<>();

        if (success) {
            int updatedPrice = menuService.calculateItemPrice(id);  // 변경된 상품 가격 계산
            int totalSum = menuService.calculateTotalSum();  // 총 금액 계산

            response.put("success", true);
            response.put("updatedPrice", updatedPrice);
            response.put("totalSum", totalSum);
        } else {
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }
}