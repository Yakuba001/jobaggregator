package com.jobaggregator.model.mapper;

import com.jobaggregator.model.JobOffer;

import java.util.List;

public interface OfferSource {

    List<JobOffer> toOffers();
}
