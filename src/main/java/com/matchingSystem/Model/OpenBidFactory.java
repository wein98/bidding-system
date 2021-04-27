package com.matchingSystem.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenBidFactory extends BidFactory {
    public OpenBid createBid(String jsonStr){
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(jsonStr, OpenBid.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
