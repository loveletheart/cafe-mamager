package myapp.controller;

import myapp.entity.*;
import myapp.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

@Controller
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ModelAndView getMenu(@RequestParam(defaultValue = "0") int page) {
    	return getMenu("coffee",page);
    }

    @GetMapping("/coffee")
    public ModelAndView getCoffee(@RequestParam(defaultValue = "0") int page) {
        return getMenu("coffee",page);
    }
    
    @GetMapping("/aid")
    public ModelAndView getAid(@RequestParam(defaultValue = "0") int page) {
        return getMenu("aid",page);
    }
    
    @GetMapping("/cookie")
    public ModelAndView getCookie(@RequestParam(defaultValue = "0") int page) {
        return getMenu("cookie",page);
    }
    
    @GetMapping("/bread")
    public ModelAndView getBread(@RequestParam(defaultValue = "0") int page) {
        return getMenu("bread",page);
    }
    
    @GetMapping("/cake")
    public ModelAndView getCake(@RequestParam(defaultValue = "0") int page) {
        return getMenu("cake",page);
    }
    
    private ModelAndView getMenu(String category,@RequestParam(defaultValue = "0") int page) {
    	
    	int pageSize = 12; // Number of items per page
        Page<Menu> menuPage = menuService.getMenuByCategory(category, page,pageSize);//데이터베이스에 메뉴의 개수 확인 및 가져오기
        ModelAndView modelAndView = new ModelAndView(category);
        modelAndView.addObject("menus", menuPage.getContent());//menus에 맞는 데이터 저장
        modelAndView.addObject("activeCategory", category);//카테고리에 맞는 버튼 색상 뒷배경과 통일
        modelAndView.addObject("totalPages", menuPage.getTotalPages()); // 전체페이지 수
        modelAndView.addObject("currentPage", page); // 현재 페이지
        System.out.println("Menus on current page: " + menuPage.getContent().size());
        return modelAndView;
    }
    
    @PostMapping("/add")//장바구니 버튼 클릭시 실행
    public ResponseEntity<String> addToCart(@RequestBody Cart cart) {
        boolean result = menuService.addToCart(cart.getMenuName());
        
        if (result) {
            return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
        } else {
            return ResponseEntity.status(500).body("장바구니 추가에 실패했습니다.");
        }
    }
    
    // 장바구니 페이지로 이동
    @GetMapping("/cart")
    public ModelAndView showCart() {
        List<Cart> cartItems = menuService.getAllCartItems();
        ModelAndView mav = new ModelAndView("cart");
        mav.addObject("cartItems", cartItems);
        return mav;
    }
    
    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCartItem(@RequestBody Map<String, Object> request) {
        String id = (String) request.get("id");
        int count = (int) request.get("count");

        // 수량 업데이트 및 가격 계산
        boolean success = menuService.updateItemCount(id, count);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            int updatedPrice = menuService.calculateItemPrice(id);
            int totalSum = menuService.calculateTotalSum();
            response.put("success", true);
            response.put("updatedPrice", updatedPrice);
            response.put("totalSum", totalSum);
        } else {
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }
}
