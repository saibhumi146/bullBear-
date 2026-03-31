package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId, Long stockId, String orderType, Integer quantity);
    List<Order> getOrdersByUser(User user);
}