package com.matchingSystem.Bidding;

import com.matchingSystem.Model.Bid;

import java.sql.Timestamp;

public class OpenBidding extends Bid {
    OpenBidding(){
        super();
    }
    public boolean isExpired(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long minutes = (interval / 1000) / 60;
        if (minutes >= 30){
            return true;
        }else {
            return false;
        }
    }
}
