package myapp.service;

import org.springframework.stereotype.Service;

import myapp.entity.Order;
import myapp.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * 개별 주문 저장
     */
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * 주문 목록 가져오기
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
