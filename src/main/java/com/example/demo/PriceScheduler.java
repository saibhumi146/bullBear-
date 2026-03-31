package com.example.demo;

import com.example.demo.model.Stock;
import com.example.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;

@Component
@EnableScheduling
public class PriceScheduler {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Random random = new Random();

    @Scheduled(fixedRate = 3000)
    public void updatePrices() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            double change = (random.nextDouble() * 6) - 3;
            double newPrice = Math.round((stock.getPrice() + change) * 100.0) / 100.0;
            if (newPrice > 0) {
                stock.setPrice(newPrice);
                stockRepository.save(stock);
            }
        }
        messagingTemplate.convertAndSend("/topic/prices", stocks);
    }
}