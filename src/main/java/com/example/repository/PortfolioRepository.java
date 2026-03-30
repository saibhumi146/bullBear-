package com.example.repository;

import com.example.demo.model.Portfolio;
import com.example.demo.model.User;
import com.example.demo.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    Optional<Portfolio> findByUserAndStock(User user, Stock stock);
}
