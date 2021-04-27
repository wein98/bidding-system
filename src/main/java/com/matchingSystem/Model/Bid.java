package com.matchingSystem.Model;

import com.matchingSystem.Poster;

import java.sql.Timestamp;

public interface Bid {
    void selectBidder(BidOffer offer);

    boolean isExpired();

    void close();
    String getDateCreated();
    String getType();
    Poster getInitiator();
    Subject getSubject();
    String getNoLessons();
    String getRate();
    String getDayTime();
}
