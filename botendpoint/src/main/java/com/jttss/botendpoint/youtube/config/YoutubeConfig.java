package com.jttss.botendpoint.youtube.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class YoutubeConfig {

    @Value("${app.youtube.apiKey}")
    private String apiKey;
}
