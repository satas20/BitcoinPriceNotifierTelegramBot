package com.example.telegrambotapp.service;

import com.example.telegrambotapp.entity.Chat;
import com.example.telegrambotapp.model.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    @Autowired
    private final ChatRepository chatRepository;

    public RepositoryService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

   public void addPrice(Long chatId, long price){
        Chat chat;
        if (chatRepository.findByChatId(chatId) == null){
            chat = new Chat();
            chat.setChatId(chatId);
        }
        else {
            chat = chatRepository.findByChatId(chatId);
        }
        chat.setNotifyPrice(price);
        chatRepository.save(chat);
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
        chatRepository.save(chat);
    }



}
