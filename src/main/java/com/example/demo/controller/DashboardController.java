package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.StockService;
import com.example.demo.service.UserService;
import com.example.demo.service.PortfolioService;
import com.example.demo.service.TiltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private TiltService tiltService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("stocks", stockService.getAllStocks());
        model.addAttribute("totalValue", portfolioService.getTotalValue(user));
        model.addAttribute("tiltMessage", tiltService.getTiltMessage(user));
        model.addAttribute("isOnTilt", tiltService.isUserOnTilt(user));
        return "dashboard";
    }
}