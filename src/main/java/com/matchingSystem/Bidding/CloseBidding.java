package com.matchingSystem.Bidding;

import com.matchingSystem.Model.Bid;

import java.sql.Timestamp;

public class CloseBidding extends Bid {
    CloseBidding(){
        super();
    }
    public boolean isExpired(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long day = (((interval / 1000) / 60)/ 60)/ 24;
        if (day >= 7){
            return true;
        }else {
            return false;
        }
    }
}
