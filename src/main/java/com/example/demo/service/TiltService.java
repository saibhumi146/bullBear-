package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TiltService {

    @Autowired
    private OrderRepository orderRepository;

    public boolean isUserOnTilt(User user) {
        List<Order> orders = orderRepository.findByUserOrderByOrderTimeDesc(user);
        if (orders.size() < 3) return false;

        int consecutiveLosses = 0;
        for (int i = 0; i < Math.min(orders.size(), 5); i++) {
            Order order = orders.get(i);
            if (order.getOrderType().equals("SELL")) {
                double invested = order.getQuantity() * order.getPrice();
                double returned = order.getQuantity() * order.getPrice();
                if (returned < invested) {
                    consecutiveLosses++;
                } else {
                    break;
                }
            }
        }
        return consecutiveLosses >= 3;
    }

    public int countRecentLosses(User user) {
        List<Order> orders = orderRepository.findByUserOrderByOrderTimeDesc(user);
        int losses = 0;
        for (Order order : orders) {
            if (order.getOrderType().equals("SELL")) {
                losses++;
                if (losses >= 3) break;
            }
        }
        return losses;
    }

    public String getTiltMessage(User user) {
        List<Order> orders = orderRepository.findByUserOrderByOrderTimeDesc(user);
        if (orders.size() < 3) return null;

        int sellCount = 0;
        int recentSells = 0;
        for (int i = 0; i < Math.min(orders.size(), 6); i++) {
            if (orders.get(i).getOrderType().equals("SELL")) {
                sellCount++;
                recentSells++;
            }
        }

        if (recentSells >= 3) {
            return "You have made " + recentSells +
                   " sell orders recently. Take a breath before your next trade — " +
                   "emotional trading leads to losses!";
        }
        return null;
    }
}