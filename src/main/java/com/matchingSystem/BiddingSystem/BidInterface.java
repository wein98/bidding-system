package com.matchingSystem.BiddingSystem;

import com.matchingSystem.Model.BidOfferModel;
import org.json.JSONObject;

import java.util.ArrayList;

public interface BidInterface {
    /**
     * Select successful bidder
     * @param offer the offer that the student choose to accept
     */
    void selectBidder(BidOfferModel offer);

    /**
     * Check if a Bid request is still valid/active.
     * Called whenever Student or Tutor trying to interact with this Bid.
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
    Poster getInitiator();
    Subject getSubject();
    JSONObject getAdditionalInfo();
    String getDateCreated();
    int getCompetencyLevel();
    ArrayList<BidOfferModel> getBidOffers();
    LessonInfo getLessonInfo();
}
