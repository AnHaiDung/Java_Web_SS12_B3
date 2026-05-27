package com.demo.ss12_b3.service;

import com.demo.ss12_b3.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private Long nextId = 1L;

    public OrderService() {
        orders.add(new Order(nextId++, "Nguyen Van A", "Laptop", 1, 1500.0));
        orders.add(new Order(nextId++, "Tran Thi B", "Mouse", 2, 40.0));
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order addOrder(Order order) {
        order.setId(nextId++);
        orders.add(order);
        return order;
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setCustomerName(order.getCustomerName());
        existingOrder.setProduct(order.getProduct());
        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setTotalAmount(order.getTotalAmount());
        return existingOrder;
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orders.remove(order);
    }
}
