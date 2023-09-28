package com.jttss.botendpoint.chatgtp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jttss.botendpoint.chatgtp.config.ChatGptConfig;
import com.jttss.botendpoint.chatgtp.entity.ChatCompletionRequestBody;
import com.jttss.botendpoint.chatgtp.entity.ChatCompletionResponseBody;
import com.jttss.botendpoint.chatgtp.entity.Message;
import com.jttss.botendpoint.chatgtp.entity.Model;
import com.jttss.botendpoint.chatgtp.exception.BizException;
import com.jttss.botendpoint.chatgtp.exception.Error;

import static com.jttss.botendpoint.chatgtp.entity.Constant.DEFAULT_CHAT_COMPLETION_API_URL;
import static com.jttss.botendpoint.chatgtp.entity.Constant.DEFAULT_MODEL;
import static com.jttss.botendpoint.chatgtp.entity.Constant.DEFAULT_USER;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <p>
 * a Java client for ChatGPT uses official API.
 * </p>
 *
 * @author <a href="https://github.com/LiLittleCat">LiLittleCat</a>
 * @since 2023/3/2
 */
@Slf4j
@Component
public class ChatGptService {

    //private final String apiKey;
    private String apiHost = DEFAULT_CHAT_COMPLETION_API_URL;
    protected OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ChatGptConfig config;

    public ChatGptService(ChatGptConfig config) {
        this.config = config;
        this.client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

//    public ChatGptService(String apiKey) {
//        this.apiKey = apiKey;
//        this.client = new OkHttpClient.Builder()
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//    }
//
//    public ChatGptService(String apiKey, OkHttpClient client) {
//        this.apiKey = apiKey;
//        this.client = client;
//    }
//
//    public ChatGptService(String apiKey, Proxy proxy) {
//        this.apiKey = apiKey;
//        client = new OkHttpClient.Builder().proxy(proxy).build();
//    }
//
//    public ChatGptService(String apiKey, String proxyHost, int proxyPort) {
//        this.apiKey = apiKey;
//        client = new OkHttpClient.Builder().
//                proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
//                .build();
//    }
//
//    public ChatGptService(String apiHost, String apiKey) {
//        this.apiHost = apiHost;
//        this.apiKey = apiKey;
//        this.client = new OkHttpClient();
//    }
//
//    public ChatGptService(String apiHost, String apiKey, OkHttpClient client) {
//        this.apiHost = apiHost;
//        this.apiKey = apiKey;
//        this.client = client;
//    }
//
//    public ChatGptService(String apiHost, String apiKey, Proxy proxy) {
//        this.apiHost = apiHost;
//        this.apiKey = apiKey;
//        client = new OkHttpClient.Builder().proxy(proxy).build();
//    }
//
//    public ChatGptService(String apiHost, String apiKey, String proxyHost, int proxyPort) {
//        this.apiHost = apiHost;
//        this.apiKey = apiKey;
//        client = new OkHttpClient.Builder().
//                proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
//                .build();
//    }


    public String ask(String input) {
        return ask(DEFAULT_MODEL.getName(), DEFAULT_USER, input);
    }

    public String ask(String user, String input) {
        return ask(DEFAULT_MODEL.getName(), user, input);
    }

    public String ask(Model model, String input) {
        return ask(model.getName(), DEFAULT_USER, input);
    }

    public String ask(Model model, String user, String input) {
        return ask(model.getName(), user, input);
    }

    private String buildRequestBody(String model, String role, String content) {
        try {
            List<Message> messages = new ArrayList<>();
            messages.add(Message.builder().role(role).content(content).build());
            ChatCompletionRequestBody requestBody = ChatCompletionRequestBody.builder()
                    .model(model)
                    .messages(messages)
                    .build();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ask for response message
     *
     * @param model
     * @param role
     * @param content
     * @return ChatCompletionResponseBody
     */
    public ChatCompletionResponseBody askOriginal(String model, String role, String content) {
        RequestBody body = RequestBody.create(buildRequestBody(model, role, content), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(apiHost)
                .header("Authorization", "Bearer " + config.getApiKey())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.body() == null) {
                    log.error("Request failed: {}, please try again", response.message());
                    throw new BizException(response.code(), "Request failed");
                } else {
                    log.error("Request failed: {}, please try again", response.body().string());
                    throw new BizException(response.code(), response.body().string());
                }
            } else {
                assert response.body() != null;
                String bodyString = response.body().string();
                return objectMapper.readValue(bodyString, ChatCompletionResponseBody.class);
            }
        } catch (IOException e) {
            log.error("Request failed: {}", e.getMessage());
            throw new BizException(Error.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * ask for response message
     *
     * @param model
     * @param role
     * @param content
     * @return String message
     */
    public String ask(String model, String role, String content) {
        ChatCompletionResponseBody chatCompletionResponseBody = askOriginal(model, role, content);
        List<ChatCompletionResponseBody.Choice> choices = chatCompletionResponseBody.getChoices();
        StringBuilder result = new StringBuilder();
        for (ChatCompletionResponseBody.Choice choice : choices) {
            result.append(choice.getMessage().getContent());
        }
        return result.toString();
    }

}
