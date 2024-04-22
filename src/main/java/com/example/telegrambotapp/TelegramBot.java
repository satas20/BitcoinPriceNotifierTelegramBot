package com.example.telegrambotapp;

import com.example.telegrambotapp.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBot extends TelegramLongPollingBot{



    private String username="satas20bot";
    private String token="7151227543:AAGVgrTTUwa1z9TnU5c8SxvQfDCjNjb-8Wc";

    @Autowired
    private  final RepositoryService repositoryService;
    public TelegramBot( RepositoryService repositoryService) throws TelegramApiException {
        this.repositoryService = repositoryService;
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
        System.out.println("TelegramBot instance created");
    }
    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            System.out.println("Received message: " + messageText+" from chatId: "+chatId);
            // Process the message and generate a response
            String[] input = messageText.split(" ");
            if(input[0].equals("/setChangeNotification")) {
                sendMessage(chatId, "You will be notified when the price of "+input[2]+" changes by more than " + input[1] + "$");

                repositoryService.setThreshHold(chatId, Integer.parseInt(input[1]));
            } else if (input[0].equals("/setPriceNotification")) {
                sendMessage(chatId, "You will be notified when the price of BTC reaches " + input[1] + "$");
                repositoryService.setPrice(chatId, Long.parseLong(input[1]));
            }
            else if (input[0].equals("/removePriceNotification")) {
                repositoryService.setPrice(chatId, -1);
                sendMessage(chatId, "Notification removed");
            }
            else if (input[0].equals("/removeChangeNotification")) {
                repositoryService.setThreshHold(chatId, -1);
                sendMessage(chatId, "Notification removed");
            }
            else {
                sendMessage(chatId, "Unknown command");
            }
        }
    }
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);
        try {
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public String processMessage(String message) {
        return "Hello, " + message;
    }
}
