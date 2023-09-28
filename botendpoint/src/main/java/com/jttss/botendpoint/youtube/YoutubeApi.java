package com.jttss.botendpoint.youtube;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.jttss.botendpoint.youtube.config.YoutubeConfig;

import static com.jttss.botendpoint.youtube.SearchContentType.VIDEO;

@Component
public class YoutubeApi {

    private static final String CLIENT_SECRETS= "/client_secret.json";
    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String WATCH_PREFIX = "https://www.youtube.com/watch?v=";

    private final YoutubeConfig config;

    public YoutubeApi(YoutubeConfig config) {
        super();
        this.config = config;
    }

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = YoutubeApi.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .build();
        Credential credential =
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        //Credential credential = authorize(httpTransport);
//        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, request -> {})
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
//    public static void main(String[] args)
//            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
//        YouTube youtubeService = getService();
//        // Define and execute the API request
//        YouTube.Search.List request = youtubeService.search()
//                .list("snippet");
//        SearchListResponse response = request.setMaxResults(25L)
//                .setQ("airplane")
//                .execute();
//        System.out.println(response);
//    }

    public String find(String query, long count) {
        YouTube youtubeService = null;
        SearchListResponse response = null;
        try {
            youtubeService = getService();
            // Define and execute the API request
            YouTube.Search.List request = youtubeService.search()
                    .list("snippet");
            response = request
                    .setKey(config.getApiKey())
                    .setMaxResults(count)
                    .setQ(query)
                    .execute();
        }
        catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        String links = response.getItems().stream().map(searchResult -> buildLink(searchResult.getId().getVideoId()))
                .collect(Collectors.joining(" "));

        return links;
    }

    public List<String> findLinks(String query, long count) {
        SearchListResponse response;
        try {
            YouTube youtubeService = getService();
            // Define and execute the API request
            YouTube.Search.List request = youtubeService.search()
                    .list("snippet");
            response = request
                    .setKey(config.getApiKey())
                    .setMaxResults(count)
                    .setType(VIDEO.stringValue())
                    .setQ(query)
                    .execute();
        }
        catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response.getItems().stream().map(searchResult -> buildLink(searchResult.getId().getVideoId())).toList();
    }

    private String buildLink(String videoId) {
        return WATCH_PREFIX + videoId;
    }
}
