package com.demo.ss12_b3.controller;

import com.demo.ss12_b3.model.Order;
import com.demo.ss12_b3.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void getAllOrders_ReturnHttp200AndJsonArray() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(
                new Order(1L, "Nguyen Van A", "Laptop", 1, 1500.0),
                new Order(2L, "Tran Thi B", "Mouse", 2, 40.0)
        ));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getOrderById_Found_ReturnHttp200() throws Exception {
        Mockito.when(orderService.getOrderById(1L))
                .thenReturn(new Order(1L, "Nguyen Van A", "Laptop", 1, 1500.0));

        mockMvc.perform(get("/api/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("Nguyen Van A"));
    }

    @Test
    void getOrderById_NotFound_ReturnHttp404() throws Exception {
        Mockito.when(orderService.getOrderById(99L)).thenThrow(new RuntimeException("Order not found"));

        mockMvc.perform(get("/api/orders/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void addOrder_ReturnHttp201AndBodyContainsId() throws Exception {
        Order response = new Order(3L, "Le Van C", "Keyboard", 1, 75.0);

        Mockito.when(orderService.addOrder(any(Order.class))).thenReturn(response);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "customerName": "Le Van C",
                                  "product": "Keyboard",
                                  "quantity": 1,
                                  "totalAmount": 75.0
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.product").value("Keyboard"));

        Mockito.verify(orderService).addOrder(any(Order.class));
    }
}
