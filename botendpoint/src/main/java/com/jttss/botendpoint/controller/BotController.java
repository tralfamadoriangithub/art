package com.jttss.botendpoint.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jttss.botendpoint.bot.BotService;
import com.jttss.botendpoint.chatgtp.ChatGptService;
import com.jttss.botendpoint.model.FillRequest;
import com.jttss.botendpoint.model.FillResponse;
import com.jttss.botendpoint.news.NewsApiService;
import com.jttss.botendpoint.news.model.News;
import com.jttss.botendpoint.news.model.NewsResponse;
import com.jttss.botendpoint.youtube.YoutubeApi;

import static com.jttss.botendpoint.utils.Utils.getLinksFromString;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BotController {

    private final ChatGptService chatGptService;
    private final NewsApiService newsApiService;
    private final YoutubeApi youtubeApiService;
    private final BotService botService;

    public BotController(ChatGptService chatGptService,
                         NewsApiService newsApiService,
                         YoutubeApi youtubeApiService,
                         BotService botService) {
        this.chatGptService = chatGptService;
        this.newsApiService = newsApiService;
        this.youtubeApiService = youtubeApiService;
        this.botService = botService;
    }

    @GetMapping("/check")
    public String check() {
        String message = "Service check ok";
        botService.postTextToChannel("@JustToTestSomeStuffChanel", "Service check ok");
        return message;
    }

    @GetMapping("/findlinks/{query}")
    public String findLinks(@PathVariable String query) {

        System.out.println(query);
        String decodedQuery;
        try {
            decodedQuery = URLDecoder.decode(query, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(decodedQuery);
        String gptAnswer = chatGptService.ask(decodedQuery);
        System.out.println(gptAnswer);
        List<String> urls = getLinksFromString(gptAnswer);
        urls.forEach(System.out::println);

        return urls.isEmpty() ? gptAnswer : gptAnswer + " " + String.join(" ", urls);
    }

    @GetMapping("/news")
    public String findNews() {
        NewsResponse news = newsApiService.getNews();

        news.getResults()
                .stream()
                .map(News::getDescription)
                .filter(Objects::nonNull)
                .forEach(m -> {
                            botService.postTextToChannel("@JustToTestSomeStuffChanel", m);
                            try {
                                Thread.sleep(3000);
                            }
                            catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

        return news.toString();
    }

    @PostMapping("/youtube")
    public FillResponse processRequest(@RequestBody FillRequest request) {

        log.info("Received request {}", request);
        List<String> searchResponseLinks =
                youtubeApiService.findLinks(request.getQuery(), request.getPostCount());

        String chatId = "@" + request.getChannel();
        if (searchResponseLinks != null) {
            searchResponseLinks.forEach(link -> botService.postTextToChannel(chatId, link));
        }

        return FillResponse.builder()
                .channel(request.getChannel())
                .source(request.getSource())
                .postCount(request.getPostCount())
                .query(request.getQuery())
                .links(searchResponseLinks)
                .build();
    }
}
