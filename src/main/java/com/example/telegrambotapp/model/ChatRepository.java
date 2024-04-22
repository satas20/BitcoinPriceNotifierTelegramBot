package com.example.telegrambotapp.model;

import com.example.telegrambotapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByChatId(Long chatId);
    @Query("SELECT c FROM Chat c WHERE c.threshold > :threshold")
    Chat findChatsNotificator(long threshold);

}
