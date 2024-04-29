package com.example.telegrambotapp.service;

import com.example.telegrambotapp.TelegramBot;
import com.example.telegrambotapp.entity.Chat;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;


@Configuration
@EnableScheduling
@Service
public class NotificatorService {
    String apiUrl = "http://localhost:8080/currPriceBTC";

    @Autowired
    TelegramBot telegramBot;
    @Autowired
    RepositoryService repositoryService;
    public HashMap<Integer, List<Chat>> changeNotificatorMap;
    public HashMap<Integer, Long> thresholdMap;

    public List<Chat> allchats;

    @PostConstruct
    public void init() {
        changeNotificatorMap = new HashMap<>();
        thresholdMap = new HashMap<>();
        // Rounded price o btc to the nearest  multiple of 10
        long roundedPrice= Math.round(getPrice()/10.0)*10;
        thresholdMap.put(10, roundedPrice);
        thresholdMap.put(100, roundedPrice);
        thresholdMap.put(200, roundedPrice);
        thresholdMap.put(500, roundedPrice);
        thresholdMap.put(1000, roundedPrice);

        loadFromDb();

      telegramBot.sendMessage(repositoryService.getAll(), "NotificatorService started." );

    }

    @Scheduled(fixedDelay = 2000)
    private void CheckPrice() {
        long price =  getPrice();
        changeNotification(price);
        priceNotification(price);
    }
    @Scheduled(fixedDelay = 2000)
    private  void loadFromDb(){
        changeNotificatorMap.put(100,repositoryService.getChatsWithThreshold(100));
        changeNotificatorMap.put(10,repositoryService.getChatsWithThreshold(10));
        changeNotificatorMap.put(200,repositoryService.getChatsWithThreshold(200));
        changeNotificatorMap.put(500,repositoryService.getChatsWithThreshold(500));
        changeNotificatorMap.put(1000,repositoryService.getChatsWithThreshold(1000));

    }
    private  List<Chat> getChatsWithThreshold(int threshold){
        return changeNotificatorMap.get(threshold);
    }
    private void changeNotification(Long price){
        for(int threshold : changeNotificatorMap.keySet()){
            int diff =  Math.abs((int) (price - thresholdMap.get(threshold)));
            if(diff >= threshold){
                long roundedPrice= Math.round(getPrice()/10.0)*10;

                telegramBot.sendMessage(getChatsWithThreshold(threshold), " Current price: " + roundedPrice + "$");
                thresholdMap.put(threshold, roundedPrice);
            }
        }
    }
    private void priceNotification(Long price){
        allchats = repositoryService.getAll();
        for(Chat chat : allchats){
            if(chat.getNotifyPrice() == -1) continue;

           if(chat.isNotifyWhenPriceHigher() && price >= chat.getNotifyPrice()){
               telegramBot.sendMessage(chat.getChatId(), "BTC exceeded the " + chat.getNotifyPrice() + "$ mark. Current price: " + price + "$");
               chat.setNotifyPrice(-1);
               chat.setNotifyWhenPriceHigher(false);
               repositoryService.setPrice(chat.getChatId(), -1);
           }
           else if(!chat.isNotifyWhenPriceHigher() && price <= chat.getNotifyPrice()){
               telegramBot.sendMessage(chat.getChatId(), "BTC fell below the " + chat.getNotifyPrice() + "$ mark. Current price: " + price + "$");
               chat.setNotifyPrice(-1);
               chat.setNotifyWhenPriceHigher(false);
               repositoryService.setPrice(chat.getChatId(), -1);
           }
        }
    }
    private long getPrice() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        System.out.println("Response: " + response);
        assert response != null;
        double priceAsDouble = Double.parseDouble(response);
        return Math.round(priceAsDouble);
    }

}

