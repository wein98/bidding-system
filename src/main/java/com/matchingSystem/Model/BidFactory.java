package com.matchingSystem.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BidFactory {
    public Bid createBid(String params, String bidType) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            if (bidType.equals("open")) {
                return objMapper.readValue(params, OpenBid.class);
            }else if (bidType.equals("close")){
                return objMapper.readValue(params, CloseBid.class);
            }else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
