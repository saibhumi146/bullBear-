package com.example.demo.service;

import com.example.demo.model.Stock;
import java.util.List;
import java.util.Optional;

public interface StockService {
    List<Stock> getAllStocks();
    Optional<Stock> findById(Long id);
    Stock findBySymbol(String symbol);
    Stock saveStock(Stock stock);
    void refreshPrices();
}