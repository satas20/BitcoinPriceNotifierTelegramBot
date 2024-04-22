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

    public void addChat(Long chatId, int threshold) {
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setThreshold(threshold);

        chatRepository.save(chat);
    }

    public void removeChat(Long chatId) {
        chatRepository.delete(chatRepository.findByChatId(chatId));
    }

    public void updateThreshold(Long chatId, int threshold) {
        Chat chat = chatRepository.findByChatId(chatId);
        chat.setThreshold(threshold);
        chatRepository.save(chat);
    }
}
