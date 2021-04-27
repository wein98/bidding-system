package com.matchingSystem.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class BidFactory {
    public Bid createBid(JSONObject params) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            if (params.getString("type").equals("open")) {
                return objMapper.readValue(params.toString(), OpenBid.class);
            }else if (params.getString("type").equals("close")){
                return objMapper.readValue(params.toString(), CloseBid.class);
            }else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
