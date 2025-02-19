package myapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import myapp.service.OrderService;
import myapp.entity.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
    private final OrderService orderService;

    // 의존성 주입 (OrderService 사용)
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 장바구니에서 넘어온 주문 데이터를 받아서 저장하는 엔드포인트
     */
    @PostMapping("/checkout")
    @ResponseBody
    public ResponseEntity<String> checkout(@RequestBody List<Order> orderRequests) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userID = authentication.getName();  // 기본적으로 username 반환
    	
    	if (orderRequests == null || orderRequests.isEmpty()) {//orderRequests안에 데이터가 없는 경우
    	    System.out.println("orderRequests가 비어 있음.");
    	    return ResponseEntity.badRequest().body("주문 데이터가 없습니다.");
    	}
    	
        for (Order order : orderRequests) {
        	order.setuserId(userID);//유저 아이디 추가
        	order.setsituation("주문완료");//주문상태변경
            orderService.saveOrder(order); // 주문 데이터 저장
        }
        return ResponseEntity.ok("주문이 완료되었습니다.");
    }


    /**
     * 주문 완료 페이지로 이동
     */
    @GetMapping("/complete")
    public String orderComplete() {
        return "/menu/coffee";
    }

    /**
     * 주문 목록을 조회하여 ods.html 화면에 전달
     */
    @GetMapping("/list")
    @ResponseBody
    public List<Order> getOrderList() {
        return orderService.getAllOrders(); // 모든 주문 데이터를 반환
    }
}
