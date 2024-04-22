package com.example.telegrambotapp.service;

import com.example.telegrambotapp.TelegramBot;
import com.example.telegrambotapp.entity.Chat;
import com.example.telegrambotapp.model.ChatRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@Service
public class NotificatorService {
    //TO BE CONSİDERED LATER:
    //1-If  the exact price is important for the threshold notify so can I group users with fixed values like 100 200 500 1000 2000 5000 10000 20000 50000 100000)
    //2- For the price notificiaton is it OK to look up the all table? is there any efficent way .?
    String apiUrl = "http://localhost:8080/currPriceBTC";
    Long chatId =  5240507145L;
    long currentPrice = 0;
    int threshold = 10;
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private ChatRepository chatRepository

    public NotificatorService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Autowired
    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }



    @PostConstruct
    public void init() {
      telegramBot.sendMessage(chatId, "NotificatorService started with chatId: " + chatId +"and threshold: "+ threshold);
      currentPrice = getPrice();
        telegramBot.sendMessage(chatId, "BTC Price: " + currentPrice +"$");
    }

    @Scheduled(fixedDelay = 2000)
    private void CheckPrice() {
        long price =  getPrice();
        long diff = Math.abs(currentPrice - price);
        chatRepository.findChatsNotificator(diff);

        currentPrice = price;

    }


    private long getPrice() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        System.out.println("Response: " + response);
        double priceAsDouble = Double.parseDouble(response);
        return Math.round(priceAsDouble);
    }

}

