package com.matchingSystem.Model;

import com.matchingSystem.Poster;

public interface Bid {
    void selectBidder(BidOffer offer);

    boolean isExpired();
    String getExpireDuration();

    void close();
    String getDateCreated();
    String getType();
    Poster getInitiator();
    Subject getSubject();
    String getNoLessons();
    String getRate();
    String getDayTime();
    int getCompetencyLevel();
}
