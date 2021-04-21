package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public interface Bid {
    void selectBidder(BidOffer offer);

    boolean isExpired();

    void close();
}
