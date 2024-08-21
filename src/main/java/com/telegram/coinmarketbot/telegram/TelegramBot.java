package com.telegram.coinmarketbot.telegram;


import com.telegram.coinmarketbot.models.CoinModel;
import com.telegram.coinmarketbot.services.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return "${bot.name}";
    }

    @Override
    public void onUpdateReceived(Update update) {
        CoinModel currencyModel = new CoinModel();
        String currency = "";

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    try {
                        currency = ApiService.getCoinsRateByNameOrSymbol(messageText, currencyModel);

                    } catch (IOException e) {
                        sendMessage(chatId, "We have not found such a currency." + "\n" +
                                "Enter the currency whose exchange rate" + "\n" +
                                "you want to know in relation to USD." + "\n" +
                                "For example: BTC");
                    }
                    sendMessage(chatId, currency);
            }
        }

    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you!" + "\n" +
                "Enter the currency whose exchange rate" + "\n" +
                "you want to know in relation to USD." + "\n" +
                "For example: ETH";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.info("Stacktrace - {}", e);
        }
    }
}

