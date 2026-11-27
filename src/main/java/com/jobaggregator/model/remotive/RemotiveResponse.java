package com.jobaggregator.model.remotive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobaggregator.http.mapper.HtmlToTextDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.List;

public record RemotiveResponse(List<Job> jobs) {
    public record Job(
            String url,
            String title,
            @JsonProperty("publication_date")
            LocalDateTime publicationDate,
            @JsonProperty("candidate_required_location")
            String candidateReqLocations,
            @JsonDeserialize(using = HtmlToTextDeserializer.class)
            String description
    ) {}
}
