package com.matchingSystem.Model;

public interface Bid {
    void selectBidder(BidOffer offer);

    boolean isExpired();

    void close();
    String getType();
}
