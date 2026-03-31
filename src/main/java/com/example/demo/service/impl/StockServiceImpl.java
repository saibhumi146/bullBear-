package com.example.demo.service.impl;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public Stock findBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void refreshPrices() {
        List<Stock> stocks = stockRepository.findAll();
        Random random = new Random();
        for (Stock stock : stocks) {
            double change = (random.nextDouble() * 10) - 5;
            double newPrice = stock.getPrice() + change;
            if (newPrice > 0) {
                stock.setPrice(Math.round(newPrice * 100.0) / 100.0);
                stockRepository.save(stock);
            }
        }
    }
}