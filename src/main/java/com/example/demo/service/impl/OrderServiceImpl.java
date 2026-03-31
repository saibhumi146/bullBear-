package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public Order placeOrder(Long userId, Long stockId, String orderType, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        double totalCost = stock.getPrice() * quantity;

        if (orderType.equals("BUY")) {
            if (user.getBalance() < totalCost) {
                throw new RuntimeException("Insufficient balance");
            }
            user.setBalance(user.getBalance() - totalCost);
            updatePortfolioBuy(user, stock, quantity, stock.getPrice());
        } else if (orderType.equals("SELL")) {
            updatePortfolioSell(user, stock, quantity, totalCost);
        }

        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStock(stock);
        order.setOrderType(orderType);
        order.setQuantity(quantity);
        order.setPrice(stock.getPrice());
        return orderRepository.save(order);
    }

    private void updatePortfolioBuy(User user, Stock stock, int quantity, double price) {
        Optional<Portfolio> existing = portfolioRepository.findByUserAndStock(user, stock);
        if (existing.isPresent()) {
            Portfolio p = existing.get();
            int newQty = p.getQuantity() + quantity;
            double newAvg = ((p.getAveragePrice() * p.getQuantity()) + (price * quantity)) / newQty;
            p.setQuantity(newQty);
            p.setAveragePrice(Math.round(newAvg * 100.0) / 100.0);
            portfolioRepository.save(p);
        } else {
            Portfolio p = new Portfolio();
            p.setUser(user);
            p.setStock(stock);
            p.setQuantity(quantity);
            p.setAveragePrice(price);
            portfolioRepository.save(p);
        }
    }

    private void updatePortfolioSell(User user, Stock stock, int quantity, double totalCost) {
        Portfolio p = portfolioRepository.findByUserAndStock(user, stock)
                .orElseThrow(() -> new RuntimeException("You don't own this stock"));
        if (p.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock quantity");
        }
        p.setQuantity(p.getQuantity() - quantity);
        user.setBalance(user.getBalance() + totalCost);
        if (p.getQuantity() == 0) {
            portfolioRepository.delete(p);
        } else {
            portfolioRepository.save(p);
        }
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderTimeDesc(user);
    }
}