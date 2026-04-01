package com.example.demo.controller;

import com.example.demo.model.Portfolio;
import com.example.demo.model.User;
import com.example.demo.service.PortfolioService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;

@Controller
public class LeaderboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/leaderboard")
    public String leaderboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email).orElseThrow();

        List<User> allUsers = userService.findAllUsers();

        List<Map<String, Object>> leaderboard = new ArrayList<>();
        for (User user : allUsers) {
            double portfolioValue = portfolioService.getTotalValue(user);
            double totalWealth = user.getBalance() + portfolioValue;

            Map<String, Object> entry = new HashMap<>();
            entry.put("name", user.getName());
            entry.put("email", user.getEmail());
            entry.put("balance", user.getBalance());
            entry.put("portfolioValue", portfolioValue);
            entry.put("totalWealth", totalWealth);
            entry.put("isCurrentUser", user.getEmail().equals(currentUser.getEmail()));
            leaderboard.add(entry);
        }

        leaderboard.sort((a, b) ->
            Double.compare((Double) b.get("totalWealth"), (Double) a.get("totalWealth")));

        model.addAttribute("leaderboard", leaderboard);
        model.addAttribute("user", currentUser);
        return "leaderboard";
    }
}