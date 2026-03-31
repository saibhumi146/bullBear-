package com.example.demo.service;

import com.example.demo.model.Portfolio;
import com.example.demo.model.User;
import java.util.List;

public interface PortfolioService {
    List<Portfolio> getPortfolioByUser(User user);
    double getTotalValue(User user);
}
