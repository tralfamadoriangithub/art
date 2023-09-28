package com.jttss.botendpoint.news;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.jttss.botendpoint.news.config.NewsApiConfig;
import com.jttss.botendpoint.news.model.NewsResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NewsApiService {

    private static final String NEWS_URL = "/news?apikey={apiKey}&language={lang}";
    private static final String SOURCES_URL = "/sources?apikey={apikey}";

    private final NewsApiConfig config;
    private final RestClient restClient;

    public NewsApiService(NewsApiConfig config) {
        this.config = config;
        this.restClient = RestClient.builder()
                .baseUrl(config.getApiUrl())
                .build();
    }

    public NewsResponse getNews() {
        return restClient.get()
                .uri(NEWS_URL, config.getApiKey(), "ru")
                .retrieve()
                .body(NewsResponse.class);
    }
}
