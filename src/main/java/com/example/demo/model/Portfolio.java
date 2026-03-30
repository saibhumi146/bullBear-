package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double averagePrice;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Stock getStock() { return stock; }
    public void setStock(Stock stock) { this.stock = stock; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getAveragePrice() { return averagePrice; }
    public void setAveragePrice(Double averagePrice) { this.averagePrice = averagePrice; }
}
