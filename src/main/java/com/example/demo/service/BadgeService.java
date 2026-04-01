package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Portfolio;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    public List<Badge> getBadges(User user) {
        List<Order> orders = orderRepository.findByUserOrderByOrderTimeDesc(user);
        List<Portfolio> portfolio = portfolioRepository.findByUser(user);
        List<Badge> badges = new ArrayList<>();

        // First Trade badge
        if (!orders.isEmpty()) {
            badges.add(new Badge("First Trade", "Placed your first order",
                    "SUCCESS", "bi-star-fill"));
        }

        // Active Trader badge
        if (orders.size() >= 5) {
            badges.add(new Badge("Active Trader", "Placed 5 or more orders",
                    "PRIMARY", "bi-lightning-fill"));
        }

        // Veteran Trader badge
        if (orders.size() >= 20) {
            badges.add(new Badge("Veteran Trader", "Placed 20 or more orders",
                    "WARNING", "bi-trophy-fill"));
        }

        // Diversified badge
        if (portfolio.size() >= 3) {
            badges.add(new Badge("Diversified", "Holding 3 or more different stocks",
                    "INFO", "bi-pie-chart-fill"));
        }

        // Risk Manager badge
        long buyCount = orders.stream()
                .filter(o -> o.getOrderType().equals("BUY")).count();
        long sellCount = orders.stream()
                .filter(o -> o.getOrderType().equals("SELL")).count();
        if (buyCount >= 3 && sellCount >= 1) {
            badges.add(new Badge("Risk Manager", "Actively managing your portfolio",
                    "DANGER", "bi-shield-fill"));
        }

        // Big Spender badge
        double totalInvested = orders.stream()
                .filter(o -> o.getOrderType().equals("BUY"))
                .mapToDouble(o -> o.getPrice() * o.getQuantity())
                .sum();
        if (totalInvested >= 10000) {
            badges.add(new Badge("Big Spender", "Invested over ₹10,000 total",
                    "WARNING", "bi-cash-stack"));
        }

        // HODLer badge
        boolean hasLargeHolding = portfolio.stream()
                .anyMatch(p -> p.getQuantity() >= 10);
        if (hasLargeHolding) {
            badges.add(new Badge("HODLer", "Holding 10+ shares of a single stock",
                    "SUCCESS", "bi-safe-fill"));
        }

        return badges;
    }

    public static class Badge {
        private String name;
        private String description;
        private String color;
        private String icon;

        public Badge(String name, String description, String color, String icon) {
            this.name = name;
            this.description = description;
            this.color = color;
            this.icon = icon;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getColor() { return color; }
        public String getIcon() { return icon; }
    }
}