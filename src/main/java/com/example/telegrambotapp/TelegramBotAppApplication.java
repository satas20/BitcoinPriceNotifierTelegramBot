package com.example.telegrambotapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TelegramBotAppApplication {

    public TelegramBotAppApplication() throws TelegramApiException {
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotAppApplication.class, args);
    }

}
