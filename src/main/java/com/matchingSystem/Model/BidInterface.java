package com.matchingSystem.Model;

import com.matchingSystem.Poster;

public interface BidInterface {
    void selectBidder(BidOfferModel offer);

    boolean isExpired();
    String getExpireDuration();

    void close();
    String getType();
    Poster getInitiator();
    Subject getSubject();
    String getNoLessons();
    String getRate();
    String getDayTime();
    String getDateCreated();
    int getCompetencyLevel();
}
