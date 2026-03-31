package com.example.demo;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void run(String... args) throws Exception {
        if (stockRepository.count() == 0) {
            stockRepository.save(new Stock(null, "TCS", "Tata Consultancy Services", 3500.00));
            stockRepository.save(new Stock(null, "INFY", "Infosys Limited", 1450.00));
            stockRepository.save(new Stock(null, "RELIANCE", "Reliance Industries", 2800.00));
            stockRepository.save(new Stock(null, "HDFC", "HDFC Bank", 1600.00));
            stockRepository.save(new Stock(null, "WIPRO", "Wipro Limited", 450.00));
            stockRepository.save(new Stock(null, "BAJAJ", "Bajaj Finance", 7200.00));
            stockRepository.save(new Stock(null, "TATAMOTORS", "Tata Motors", 620.00));
            System.out.println("Stock data loaded successfully!");
        }
    }
}