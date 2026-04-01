package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.BadgeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    @GetMapping("/badges")
    public String badges(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElseThrow();
        model.addAttribute("badges", badgeService.getBadges(user));
        model.addAttribute("user", user);
        return "badges";
    }
}