package com.jobaggregator.client;

import com.jobaggregator.http.HttpClientProvider;
import com.jobaggregator.http.ObjectMapperProvider;
import com.jobaggregator.model.remotive.RemotiveResponse;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RemotiveJobClient {

    private static final String BASE_URL = "https://remotive.com/api/remote-jobs";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public RemotiveJobClient() {
        this.client = HttpClientProvider.getClient();
        this.mapper = ObjectMapperProvider.getMapper();
    }

    public CompletableFuture<RemotiveResponse> fetchJobsJson(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        URI uri = URI.create(BASE_URL + "?search=" + encodedQuery);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .header("User-Agent", "JavaJobAggregator")
                .GET()
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                   if (response.statusCode() == 200) {
                       try {
                           return mapper.readValue(response.body(), RemotiveResponse.class);
                       } catch (JacksonException e) {
                           throw new RuntimeException("Failed deserialization", e);
                       }
                   } else throw new RuntimeException("Failed, response code:" + response.statusCode());
                });
    }
}
