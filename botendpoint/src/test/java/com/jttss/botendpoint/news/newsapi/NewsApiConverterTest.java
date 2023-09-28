package com.jttss.botendpoint.news.newsapi;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jttss.botendpoint.news.model.NewsResponse;
import com.jttss.botendpoint.news.model.SourcesResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewsApiConverterTest {

    private final static ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    public void testNewsResponse() throws IOException {
        File file = new File("src/test/resources/json/news/newsapi/newsapi_response.json");
        NewsResponse response = MAPPER.readValue(file, NewsResponse.class);
        Assert.notNull(response, "Object null");
    }

    @Test
    public void testSourcesResponseBy() throws IOException {
        File file = new File("src/test/resources/json/news/newsapi/newsapi_sources_by.json");
        SourcesResponse response = MAPPER.readValue(file, SourcesResponse.class);
        Assert.notNull(response, "Object null");
    }

    @Test
    public void testSourcesResponseUa() throws IOException {
        File file = new File("src/test/resources/json/news/newsapi/newsapi_sources_ua.json");
        SourcesResponse response = MAPPER.readValue(file, SourcesResponse.class);
        Assert.notNull(response, "Object null");
    }

    @Test
    public void testSourcesResponseRu() throws IOException {
        File file = new File("src/test/resources/json/news/newsapi/newsapi_sources_ru.json");
        SourcesResponse response = MAPPER.readValue(file, SourcesResponse.class);
        Assert.notNull(response, "Object null");
    }
}
