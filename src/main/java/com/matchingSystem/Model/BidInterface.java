package com.matchingSystem.Model;

import com.matchingSystem.Poster;

public interface BidInterface {
    /**
     * Select successful bidder
     * @param offer the offer that the student choose to accept
     */
    void selectBidder(BidOfferModel offer);

    /**
     * Check if a Bid request is still valid/active
     * @return true if the request already expired, otherwise false
     */
    boolean isExpired();

    /**
     * Get expire time duration
     * @return String of the time duration in minutes or days
     */
    String getExpireDuration();

    /**
     * Close out the Bid Request when no action is carried out by the student before expiry
     */
    void close();

    // getters
    String getId();
    String getType();
    String getDuration();
    Poster getInitiator();
    Subject getSubject();
    String getNoLessons();
    String getRate();
    String getDayTime();
    String getDateCreated();
    int getCompetencyLevel();
}
