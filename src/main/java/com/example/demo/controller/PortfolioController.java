package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.PortfolioService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    @GetMapping("/portfolio")
    public String portfolio(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElseThrow();
        model.addAttribute("portfolio", portfolioService.getPortfolioByUser(user));
        model.addAttribute("totalValue", portfolioService.getTotalValue(user));
        model.addAttribute("user", user);
        return "portfolio";
    }
}

