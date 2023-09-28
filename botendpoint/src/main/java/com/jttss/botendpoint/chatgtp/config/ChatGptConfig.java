package com.jttss.botendpoint.chatgtp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class ChatGptConfig {

    @Value("${app.gpt.apiKey}")
    private String apiKey;
}
