package com.jobaggregator.model.remotive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobaggregator.http.mapper.HtmlToTextDeserializer;
import com.jobaggregator.model.JobOffer;
import com.jobaggregator.model.mapper.OfferSource;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.List;

public record RemotiveResponse(List<Job> jobs) implements OfferSource {

    @Override
    public List<JobOffer> toOffers() {
        if (this.jobs == null || jobs.isEmpty()) {
            return List.of();
        }
        return this.jobs.stream()
                .map(job -> new JobOffer(
                        "Remotive",
                        job.url(),
                        job.title(),
                        job.publicationDate(),
                        job.candidateReqLocations(),
                        job.description()
                        )).toList();
    }

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
