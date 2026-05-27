package com.demo.ss12_b3.service;

import com.demo.ss12_b3.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void getAllOrders_ReturnNonEmptyList() {
        List<Order> orders = orderService.getAllOrders();

        assertFalse(orders.isEmpty());
    }

    @Test
    void getOrderById_Found() {
        Order order = orderService.getOrderById(1L);

        assertEquals(1L, order.getId());
        assertEquals("Nguyen Van A", order.getCustomerName());
    }

    @Test
    void getOrderById_NotFound_ThrowException() {
        assertThrows(RuntimeException.class, () -> orderService.getOrderById(99L));
    }

    @Test
    void addOrder_Success() {
        Order order = new Order(null, "Le Van C", "Keyboard", 1, 75.0);

        Order result = orderService.addOrder(order);

        assertNotNull(result.getId());
        assertEquals(3L, result.getId());
        assertEquals("Keyboard", result.getProduct());
    }

    @Test
    void updateOrder_Success() {
        Order updateOrder = new Order(null, "Pham Thi D", "Monitor", 2, 400.0);

        Order result = orderService.updateOrder(1L, updateOrder);

        assertEquals(1L, result.getId());
        assertEquals("Pham Thi D", result.getCustomerName());
        assertEquals("Monitor", result.getProduct());
        assertEquals(2, result.getQuantity());
        assertEquals(400.0, result.getTotalAmount());
    }

    @Test
    void deleteOrder_RemovesElement() {
        int beforeDelete = orderService.getAllOrders().size();

        orderService.deleteOrder(1L);

        assertEquals(beforeDelete - 1, orderService.getAllOrders().size());
        assertThrows(RuntimeException.class, () -> orderService.getOrderById(1L));
    }
}
