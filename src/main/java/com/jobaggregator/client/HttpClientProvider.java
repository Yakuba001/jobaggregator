package com.jobaggregator.client;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class HttpClientProvider {

    private static final HttpClient CLIENT;
    private static final ExecutorService EXECUTOR;

    static {
        EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
        CLIENT = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .executor(EXECUTOR)
                .build();
    }

    private HttpClientProvider() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static HttpClient getClient() {
        return CLIENT;
    }

    public static void shutdown() {
        CLIENT.close();
        EXECUTOR.close();
    }
}
