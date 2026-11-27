package com.jobaggregator;

import com.jobaggregator.client.RemotiveJobClient;
import com.jobaggregator.http.HttpClientProvider;
import com.jobaggregator.model.remotive.RemotiveResponse;
import com.jobaggregator.output.ConsolePrinter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {

    static void main() {
        RemotiveJobClient remotiveJobClient = new RemotiveJobClient();
        List<String> queries = List.of("Java", "Python");
        List<CompletableFuture<RemotiveResponse>> allRequests = queries.stream()
                .map(remotiveJobClient::fetchJobsJson)
                .toList();
        List<RemotiveResponse> allResults = allRequests.stream()
                .map(CompletableFuture::join)
                .toList();
        ConsolePrinter.print(allResults);

        HttpClientProvider.shutdown();
    }
}
