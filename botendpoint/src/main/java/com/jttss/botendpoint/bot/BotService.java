package com.jttss.botendpoint.bot;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.jttss.botendpoint.bot.config.BotConfig;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BotService {

    private final BotConfig config;

    public BotService(BotConfig config) {
        this.config = config;
    }

    public void postTextToChannel(String chatId, String message) {

        if (StringUtils.isBlank(chatId) || StringUtils.isBlank(message)) {
            return;
        }

        try {
            RestClient restClient = RestClient.builder().baseUrl("https://api.telegram.org").build();
            String postTextUrl = "/bot{botApi}/sendMessage?chat_id={chatId}&text={text}";
            String response = restClient
                    .get()
                    .uri(postTextUrl, config.getApiKey(), chatId, message)
                    .retrieve()
                    .body(String.class);
            log.info(response);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
