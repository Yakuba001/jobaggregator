package com.jobaggregator.model.remotive;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record RemotiveResponse(List<Job> jobs) {
    public record Job(
            Long id,
            String url,
            String title,
            @JsonProperty("publication_date")
            LocalDateTime publicationDate,
            @JsonProperty("candidate_required_location")
            String candidateReqLocations,
            String description
    ) {}
}
