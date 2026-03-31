package com.example.demo.service.impl;

import com.example.demo.model.Portfolio;
import com.example.demo.model.User;
import com.example.demo.repository.PortfolioRepository;
import com.example.demo.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public List<Portfolio> getPortfolioByUser(User user) {
        return portfolioRepository.findByUser(user);
    }

    @Override
    public double getTotalValue(User user) {
        List<Portfolio> portfolio = portfolioRepository.findByUser(user);
        return portfolio.stream()
                .mapToDouble(p -> p.getStock().getPrice() * p.getQuantity())
                .sum();
    }
}

