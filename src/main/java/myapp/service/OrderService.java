package myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import myapp.entity.Order;
import myapp.repository.CartRepository;
import myapp.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
	
    private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	
	public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * 개별 주문 저장
     * @return 
     */
    @Transactional
    public boolean processOrder(List<Order> orderRequests, String userId) {
    	try {
            // 주문 저장
            for (Order order : orderRequests) {
            	System.out.println("DB에 저장: " + order.getMenuName() + ", 수량: " + order.getQuantity());
                order.setuserId(userId);
                order.setsituation("주문완료");
                orderRepository.save(order);
                orderRepository.flush();
            }

            // 주문이 완료되면 장바구니 삭제
            cartRepository.deleteByUserId(userId);

            return true;  // 주문 및 장바구니 삭제 성공
        } catch (Exception e) {
            return false; // 주문 실패
        }
    }

    /**
     * 주문 목록 가져오기
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
