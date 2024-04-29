package com.example.telegrambotapp.service;

import com.example.telegrambotapp.entity.Chat;
import com.example.telegrambotapp.model.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {

    @Autowired
    private final ChatRepository chatRepository;
    String apiUrl = "http://localhost:8080/currPriceBTC";



    public RepositoryService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public void setThreshHold(Long chatId, int threshold){
        Chat chat;
        if (chatRepository.findByChatId(chatId) == null){
            chat = new Chat();
            chat.setChatId(chatId);
        }
        else {
            chat = chatRepository.findByChatId(chatId);
        }
        chat.setThreshold(threshold);
        chatRepository.save(chat);
    }
    public void setPrice(Long chatId, long price){
        Chat chat;
        if (chatRepository.findByChatId(chatId) == null){
            chat = new Chat();
            chat.setChatId(chatId);
        }
        else {
            chat = chatRepository.findByChatId(chatId);
        }
        chat.setNotifyPrice(price);
        if(price > getPrice()){
            chat.setNotifyWhenPriceHigher(true);
        }
        else {
            chat.setNotifyWhenPriceHigher(false);
        }
        chatRepository.save(chat);
    }
    public List<Chat> getChatsWithThreshold(int threshold) {
        return chatRepository.findByThreshold(threshold);
    }

    public List<Chat> getAll() {
        return chatRepository.findAll();
    }
    private long getPrice() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        System.out.println("Response: " + response);
        double priceAsDouble = Double.parseDouble(response);
        return Math.round(priceAsDouble);
    }
}
