package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAllUsers();
    User updateUser(User user);
    void deleteUser(Long id);
}