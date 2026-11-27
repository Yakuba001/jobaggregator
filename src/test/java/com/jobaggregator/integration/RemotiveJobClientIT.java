package com.jobaggregator.integration;

import com.jobaggregator.client.RemotiveJobClient;
import com.jobaggregator.model.JobOffer;
import com.jobaggregator.model.remotive.RemotiveResponse;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RemotiveJobClientIT {

    private HttpServer server;
    private RemotiveJobClient client;

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        int port = server.getAddress().getPort();
        client = new RemotiveJobClient("http://localhost:" + port + "/api/remote-jobs");
    }

    @AfterEach
    void tearDown() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void fetchJobsJson_shouldFetchParseAndCleanHtml_whenResponseIsOk() {
        String mockJsonResponse = """
                {
                  "job-count": 1,
                  "jobs": [
                    {
                      "id": 2091165,
                      "url": "https://remotive.com",
                      "title": "Backend Developer",
                      "publication_date": "2026-10-29T18:22:41",
                      "candidate_required_location": "Worldwide",
                      "description": "<p>We\\u2019re looking for a <strong>Back Developer</strong> &amp; architect.</p>"
                    }
                  ]
                }
                """;

        server.createContext("/api/remote-jobs", exchange -> {
            assertThat(exchange.getRequestMethod()).isEqualTo("GET");
            assertThat(exchange.getRequestURI().getQuery()).isEqualTo("search=java+developer");
            assertThat(exchange.getRequestHeaders().getFirst("Accept")).isEqualTo("application/json");
            byte[] responseBytes = mockJsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        });
        server.start();

        RemotiveResponse response = client.fetchJobsJson("java developer").join();
        List<JobOffer> offers = response.toOffers();

        assertThat(offers).hasSize(1);
        JobOffer jobOffer = offers.getFirst();
        assertThat(jobOffer.source()).isEqualTo("Remotive");
        assertThat(jobOffer.title()).isEqualTo("Backend Developer");
        assertThat(jobOffer.location()).isEqualTo("Worldwide");
        assertThat(jobOffer.description()).isEqualTo("We’re looking for a Back Developer & architect.");
    }
}
