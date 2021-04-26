package com.matchingSystem.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CloseBidFactory extends BidFactory {
    public CloseBid createBid(String params){
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(params,CloseBid.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
