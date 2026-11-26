package com.jobaggregator;

import com.jobaggregator.client.HttpClientProvider;

import java.net.http.HttpClient;

public class Main {

    static void main() {
        HttpClient httpClient = HttpClientProvider.getClient();



        HttpClientProvider.shutdown();
    }
}
