package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order/place")
    public String placeOrder(@RequestParam Long stockId,
                             @RequestParam String orderType,
                             @RequestParam Integer quantity,
                             Authentication authentication,
                             Model model) {
        try {
            String email = authentication.getName();
            User user = userService.findByEmail(email).orElseThrow();
            orderService.placeOrder(user.getId(), stockId, orderType, quantity);
            return "redirect:/dashboard?success";
        } catch (Exception e) {
            return "redirect:/dashboard?error=" + e.getMessage();
        }
    }

    @GetMapping("/orders")
    public String orderHistory(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElseThrow();
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        model.addAttribute("user", user);
        return "orders";
    }
}