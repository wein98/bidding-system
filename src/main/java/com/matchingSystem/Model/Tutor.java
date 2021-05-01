package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tutor extends User {
    public Tutor(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Tutor() {
        super();
    }

    /**
     * Function to send an offer for Open Bidding
     * @param bidId
     * @param additionalObject
     */
    public void makeOfferToOpenBidding(String bidId, JSONObject additionalObject) {
        OpenBid bid = (OpenBid) APIFacade.getBidById(bidId);
        JSONObject additionalInfo = bid.getAdditionalInfo();
        JSONArray bidOffersArr = additionalInfo.getJSONArray("bidOffers");
        int toRemoveIndex = -1;

        // look for the bidoffers offered by the tutorId previously
        for (int i=0; i<bidOffersArr.length(); i++) {
            JSONObject o = bidOffersArr.getJSONObject(i);
            if (this.id.equals(o.getString("tutorId"))) {
                toRemoveIndex = i;
                break;
            }
        }

        // remove old bidoffer and add new updated one
        if (toRemoveIndex >= 0) {    // if there's a previous bid offers offered
            bidOffersArr.remove(toRemoveIndex);
        }
        bidOffersArr.put(additionalObject);

        // remove the whole list and insert again
        additionalInfo.remove("bidOffers");
        additionalInfo.put("bidOffers", bidOffersArr);

        APIFacade.updateBidById(bidId, additionalInfo);
    }

//    public void buyOutOpenBid(OpenBid bid, BidOfferModel offer){
//        int requiredCompetency = bid.getCompetencyLevel();
//        Subject requiredSubject = bid.getSubject();
//        for(Competency competency: this.competencies){
//            if(competency.getSubject().getId().equals(requiredSubject.getId())){
//                if(competency.getLevel() >= requiredCompetency + 2){
//                    // able to trigger buy out of bid
//                    bid.selectBidder(offer);
//                }
//            }
//        }
//    }
//
//    /**
//     * Function to send a message for Close Bidding
//     * @param bidId
//     * @param messageContent
//     */
//    public void sendMessage(String bidId, JSONObject messageContent) {
//
//    }

    public int getCompetencyLvlFromSubject(Subject s) {
        for (Competency c: competencies) {
            if (c.getSubject().equals(s)) {
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

}
