package com.matchingSystem.Model;

import com.matchingSystem.Poster;

public interface Bid {
    void selectBidder(BidOffer offer);

    boolean isExpired();

    void close();
    String getType();
    Poster getInitiator();
    Subject getSubject();
    String getNoLessons();
    String getRate();
    String getDayTime();
}
