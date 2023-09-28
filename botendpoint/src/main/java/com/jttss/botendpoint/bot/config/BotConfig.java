package com.jttss.botendpoint.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class BotConfig {

    @Value("${app.bot.apiKey}")
    private String apiKey;
}
