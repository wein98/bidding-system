package com.matchingSystem.LoginSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Bid;
import com.matchingSystem.BiddingSystem.Competency;
import com.matchingSystem.BiddingSystem.OpenBid;
import com.matchingSystem.BiddingSystem.Subject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Tutor extends User implements Observer {
    protected ArrayList<Bid> subscribedBids = new ArrayList<>();

    public Tutor(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Tutor() {
        super();
    }

    public void setSubscribedBids() {
        this.subscribedBids = APIFacade.getSubscribedBids(getId());
    }

    public ArrayList<Bid> getSubscribedBids() {
        return subscribedBids;
    }

    public int getCompetencyLvlFromSubject(Subject s) {
        for (Competency c: competencies) {
            if (c.getSubject().getId().equals(s.getId())) {
                return c.getLevel();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", competencies=" + competencies +
                ", qualifications=" + qualifications +
                ", contracts=" + contracts +
                ", objectMapper=" + objectMapper +
                '}';
    }

    @Override
    public void update(Observable o, Object arg) {
        // set tutor's subscribed bids when tutor pressed subscribe to open bid button
        if (o instanceof OpenBid) {
            this.setSubscribedBids();
        }
    }
}
