package com.jttss.botendpoint.news.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class NewsApiConfig {

    @Value("${app.news.newsApi.apiKey}")
    private String apiKey;

    @Value("${app.news.newsApi.url}")
    private String apiUrl;

}
