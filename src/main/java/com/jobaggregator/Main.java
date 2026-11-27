package com.jobaggregator;

import com.jobaggregator.client.RemotiveJobClient;
import com.jobaggregator.http.HttpClientProvider;
import com.jobaggregator.model.remotive.RemotiveResponse;

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
        temporaryMapper(allResults);

        HttpClientProvider.shutdown();
    }

    private static void temporaryMapper(List<RemotiveResponse> responses) {
        responses.stream()
                .flatMap(response -> response.jobs().stream())
                .map(job -> """
                        URL: %s
                        Title: %s
                        Date: %s
                        Location: %s
                        Description: %s
                        """.formatted(
                        job.url(),
                        job.title(),
                        job.publicationDate(),
                        job.candidateReqLocations(),
                        job.description().substring(0, Math.min(job.description().length(), 150)) + "..."
                ))
                .forEach(System.out::println);
    }
}
