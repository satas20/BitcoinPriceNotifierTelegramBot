package com.example.telegrambotapp.service;

import com.example.telegrambotapp.TelegramBot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@Service
public class NotificatorService {
    String apiUrl = "http://localhost:8080/currPriceBTC";
    Long chatId =  5240507145L;
    long currentPrice = 0;
    int threshold = 10;

    @Autowired
    TelegramBot telegramBot;

    @PostConstruct
    public void init() {
      telegramBot.sendMessage(chatId, "NotificatorService started with chatId: " + chatId +"and threshold: "+ threshold);
      currentPrice = getPrice();
        telegramBot.sendMessage(chatId, "BTC Price: " + currentPrice +"$");
    }

    @Scheduled(fixedDelay = 2000)
    private void CheckPrice() {
        long price =  getPrice();
        if(Math.abs(currentPrice - price) > threshold) {
            telegramBot.sendMessage(chatId, "BTC Price: " + price +"$");
            currentPrice = price;
        }

    }


    private long getPrice() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        System.out.println("Response: " + response);
        double priceAsDouble = Double.parseDouble(response);
        return Math.round(priceAsDouble);
    }

}

