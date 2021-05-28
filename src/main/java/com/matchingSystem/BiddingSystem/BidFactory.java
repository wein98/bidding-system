package com.matchingSystem.BiddingSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class BidFactory {
    /**
     * Factory method of creating a new Bid subclass object
     * @param params bid details
     * @return the created Bid subclass object
     */
    public Bid createBid(JSONObject params) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            if (params.has("type")) {
                if (params.getString("type").equals("open")) {
                    return objMapper.readValue(params.toString(), OpenBid.class);
                }else if (params.getString("type").equals("close")){
                    return objMapper.readValue(params.toString(), CloseBid.class);
                }else{
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
