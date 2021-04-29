package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Poster;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.Map;

public class Message{
    @JsonProperty("id")
    private String id;
    @JsonProperty("bidId")
    private String bidId;
    @JsonProperty("poster")
    private Poster poster;
    @JsonProperty("datePosted")
    private Timestamp datePosted;
    @JsonProperty("dateLastEdited")
    private Timestamp dateLastEdited;
    @JsonProperty("content")
    private String content;
    @JsonProperty("additionalInfo")
    private JSONObject additionalInfo;

    private BidOfferModel linkedOffer; // the Offer that this message is linked to

    Message(){}

    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);
        }else{
            this.additionalInfo = new JSONObject();
        }
    }
    /**
     * Get the attached offer offered by tutor
     * @return
     */
    public BidOfferModel getLinkedOffer(){
        return this.linkedOffer;
    }
    /**
     * Attach the offer that this message is linked to OR update the offer
     * @param offer
     */
    public void attachOffer(BidOfferModel offer){
        this.linkedOffer = offer;
    }

    /**
     * Update new content in the conversation
     * @param content
     */
    public void tutorUpdateMessageContent(String content) {
        StringBuilder params = APIFacade.getMessageAPI().parseToJsonForPartialUpdate(content, getAdditionalInfo());
        APIFacade.getMessageAPI().updatePartialById(getId(), params);
    }

    /**
     * Function for Student to reply the message.
     * @param msgContent
     */
    public void studentReplyMsg(String msgContent) {
        additionalInfo.put("studentReply", msgContent);
        StringBuilder params = APIFacade.getMessageAPI().parseToJsonForPartialUpdate(getContent(), getAdditionalInfo());
        APIFacade.getMessageAPI().updatePartialById(getId(), params);
    }

    public String getId() {
        return id;
    }

    public Poster getPoster() {
        return poster;
    }

    /**
     * Get tutor's message.
     * @return tutor's message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Get student's reply.
     * @return student's reply message.
     */
    public String getStudentReply() {
        if (additionalInfo != null) {
            return additionalInfo.getString("studentReply");
        }
        return null;
    }

    public JSONObject getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public String toString() {
        return "messageId: " + this.id + "\nbidId: " + this.bidId + "\nposter" + this.poster.toString()+ "\ncontent: " + this.content;
    }
}
