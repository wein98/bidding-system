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
        OpenBid bid = (OpenBid) APIFacade.getBidAPI().getById(bidId, Constant.NONE);
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

        StringBuilder params = APIFacade.getBidAPI().parseToJsonForPartialUpdate(additionalInfo);
        APIFacade.getBidAPI().updatePartialById(bidId, params);
    }

    // TODO: student should also have the function to reply to a message that is sent by a tutor on a close bid

    /**
     * Function to send a message for Close Bidding
     * @param bidId
     * @param messageContent
     */
    public void sendMessage(String bidId, JSONObject messageContent) {

    }

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
        return "Tutor{" + "id='" + id + '\'' + ", givenName='" + givenName + '\'' + ", familyName='" + familyName + '\'' + ", userName='" + userName + '\'' + ", competencies=" + competencies + ", qualifications=" + qualifications + ", contracts=" + contracts + '}';
    }

}
