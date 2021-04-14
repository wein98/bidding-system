package com.matchingSystem.Bidding;

import com.matchingSystem.Model.Bid;
import com.matchingSystem.Model.Message;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CloseBidding extends Bid {

    private ArrayList<Message> messages;

    CloseBidding(){
        super();
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }
    public ArrayList<Message> getMessages(){
        return this.messages;
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
