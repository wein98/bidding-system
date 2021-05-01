package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;

public class CloseBid extends Bid {

    private Message tutorMessage;
    private Message studentMessage;

    public CloseBid(){
        super();
    }

    @Override
    public boolean isExpired() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long day = (((interval / 1000) / 60)/ 60)/ 24;
        if (day >= 7){
            // automatically closes the bid
            additionalInfo.put("successfulBidder","undefined");
            // call update bid API
            APIFacade.updateBidById(getId(), getAdditionalInfo());

            close();
            return true;

        } else {
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
        String tutorId = bidOffer.getString("offerTutorId");
        int toRemoveIndex = -1;

        // check if there's any other bid offers to find previously offered by this tutor
        if (getBidOffers() != null) {
            // look for the bidoffers offered by the tutorId previously
            for (int i=0; i<getBidOffers().size(); i++) {
                if (tutorId.equals(getBidOffers().get(i).getOfferTutorId())) {
                    toRemoveIndex = i;
                    break;
                }
            }
        }

        // remove old bidoffer and add new updated one
        if (toRemoveIndex >= 0) {    // if there's a previous bid offers offered
            // get bid offer msgId
            String msgId = getBidOffers().get(toRemoveIndex).getMsgId();
            Message msg = APIFacade.getMessageById(msgId);
            msg.tutorUpdateMessageContent(bidOffer.getString("msgContent"));

            // remove this old bid offer
            getBidOffers().remove(toRemoveIndex);

            // attach old bidOffer's msdId to this bidoffer
            bidOffer.put("msgId", msgId);

        } else {    // else it's a new bid offer
            // create a msg object for this bid offer
            Message msgObj = APIFacade.createMessage(
                    getId(),
                    getId(),
                    bidOffer.getString("msgContent")
            );
            // attach msgId to this bidoffer
            bidOffer.put("msgId", msgObj.getId());
        }

        JSONArray bidOffers = new JSONArray(getBidOffers());
        bidOffers.put(bidOffer);

        // remove the whole list and insert again
        getAdditionalInfo().remove("bidOffers");
        getAdditionalInfo().put("bidOffers", bidOffers);

        APIFacade.updateBidById(getId(), getAdditionalInfo());
    }

    public void updateTutorMsg(String content){
        this.tutorMessage.tutorUpdateMessageContent(content);
    }

    public void updateStudentMsg(String content){
        this.studentMessage.tutorUpdateMessageContent(content);
    }

    public void setTutorMessage(Message message){
        this.tutorMessage = message;
    }

    public void setStudentMessage(Message message) {
        this.studentMessage = message;
    }

    /**
     * Retrieve the offer attached in a conversation
     * @param message the message referred to
     * @return the offer attached to this message
     */
    public BidOfferModel getOfferFromMessage(Message message){
        return message.getLinkedOffer();
    }

    public Message getTutorMessage(){
        return this.tutorMessage;
    }

    public Message getStudentMessage() {
        return studentMessage;
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
                '}';
    }
}
