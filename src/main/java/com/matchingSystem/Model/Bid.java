package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Poster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// holds the common fields of OpenBid and CloseBid
public abstract class Bid implements BidInterface{
    @JsonProperty("id")
    protected String id;
    @JsonProperty("type")
    protected String type;
    @JsonProperty("initiator")
    protected Poster initiator;
    @JsonProperty("dateCreated")
    protected Timestamp dateCreated;
    @JsonProperty("dateClosedDown")
    protected Timestamp dateClosedDown;
    @JsonProperty("subject")
    protected Subject subject;
    @JsonProperty("additionalInfo")
    protected JSONObject additionalInfo;

    @JsonProperty(value = "messages",required = false)
    protected List<Message> messages;

    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);
        }
    }

    @Override
    public ArrayList<BidOfferModel> getBidOffers() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BidOfferModel> retVal = new ArrayList<>();
        if (additionalInfo != null) {
            JSONArray arr = additionalInfo.getJSONArray("bidOffers");

            if (arr.length() != 0) {
                for (int i=0; i<arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
//                    BidOfferModel b = new BidOfferModel(this);
                    retVal.add(new BidOfferModel(this, o));
                }
            }
            return retVal;
        }

        return null;
    }
}
