package com.matchingSystem.BiddingSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Model.BidOfferModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CloseBid extends Bid {

    protected boolean closed = false; // indicate if a Bid is closed
    private Message tutorMessage;
    private Message studentMessage;
    protected ArrayList<BidOfferModel> bidders;

    public CloseBid(){
        super();
    }

    @Override
    public boolean isExpired(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long day = (((interval / 1000) / 60)/ 60)/ 24;
        if (day >= 7){
            // automatically closes the bid
            additionalInfo.put("successfulBidder","undefined");
            // call update bid API
            APIFacade.updateBidById(getId(), getAdditionalInfo());

            this.close();;
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String getExpireDuration() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long day = (((interval / 1000) / 60)/ 60)/ 24;

        if (day > 0) {
            return day + " days";
        } else {
            long minutes = (interval / 1000) / 60;
            return minutes + " minutes";
        }
    }

    /**
     * Tutor offers an open bid.
     * @param bidOffer
     */
    public void tutorOfferBid(JSONObject bidOffer) {
        bidders = getBidOffers();
        String tutorId = bidOffer.getString("offerTutorId");
        int toRemoveIndex = -1;

        // check if there's any other bid offers to find previously offered by this tutor
        if (bidders != null) {
            // look for the bidoffers offered by the tutorId previously
            for (int i=0; i<bidders.size(); i++) {
                if (tutorId.equals(bidders.get(i).getOfferTutorId())) {
                    toRemoveIndex = i;
                    break;
                }
            }
        }

        // remove old bidoffer and add new updated one
        if (toRemoveIndex >= 0) {    // if there's a previous bid offers offered
            // get bid offer msgId
            String msgId = bidders.get(toRemoveIndex).getMsgId();
            Message msg = APIFacade.getMessageById(msgId);
            msg.tutorUpdateMessageContent(bidOffer.getString("msgContent"));

            // remove this old bid offer
            bidders.remove(toRemoveIndex);

            // attach old bidOffer's msdId to this bidoffer
            bidOffer.put("msgId", msgId);

        } else {    // else it's a new bid offer
            // create a msg object for this bid offer
            Message msgObj = APIFacade.createMessage(
                    getId(),
                    tutorId,
                    bidOffer.getString("msgContent")
            );
            // attach msgId to this bidoffer
            bidOffer.put("msgId", msgObj.getId());
        }

        JSONArray bidOffers = new JSONArray(bidders);
        bidOffers.put(bidOffer);

        // remove the whole list and insert again
        getAdditionalInfo().remove("bidOffers");
        getAdditionalInfo().put("bidOffers", bidOffers);

        APIFacade.updateBidById(getId(), getAdditionalInfo());
    }

    @Override
    public String toString() {
        return "CloseBid{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", initiator=" + initiator +
                ", dateCreated=" + dateCreated +
                ", dateClosedDown=" + dateClosedDown +
                ", subject=" + subject +
                ", additionalInfo=" + additionalInfo +
                ", closed=" + closed +
                '}';
    }
}
