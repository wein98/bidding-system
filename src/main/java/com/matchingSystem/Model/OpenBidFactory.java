package com.matchingSystem.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenBidFactory extends BidFactory {
    public OpenBid createBid(String params){
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(params, OpenBid.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
