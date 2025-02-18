package myapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import myapp.service.OrderService;
import myapp.entity.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

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
        for (Order order : orderRequests) {
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
