package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIAdapters.*;
import com.matchingSystem.API.ClientInterfaces.*;
import com.matchingSystem.BiddingSystem.*;
import com.matchingSystem.Constant;
import com.matchingSystem.ContractDev.Contract;
import com.matchingSystem.LoginSystem.Qualification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class APIFacade {
    private static APIFacade ourInstance;
    private static final ContractAPIInterface contractAPI = new ContractAPI();
    private static final BidAPIInterface bidAPI = new BidAPI();
    private static final MessageAPIInterface messageAPI = new MessageAPI();
    private static final UserAPIInterface userAPI = new UserAPI();
    private static final SubjectAPIInterface subjectAPI = new SubjectAPI();
    private static final CompetencyAPIInterface competencyAPI = new CompetencyAPI();
    private static final QualificationAPIInterface qualificationAPI = new QualificationAPI();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private APIFacade() {}

    public static APIFacade getInstance() {
        if (ourInstance == null) {
            ourInstance = new APIFacade();
        }
        return ourInstance;
    }

    public static ContractAPIInterface getContractAPI() {
        return contractAPI;
    }

    // User APIs
    public static JSONObject getUserById(String id) {
        return (JSONObject) userAPI.getById(id, Constant.NONE);
    }

    /**
     * Tutor subscribes to an active bid.
     * @param id tutor's user id
     * @param bidId to subscribe bid id
     */
    public static void subscribeBid(String id, String bidId) {
        JSONObject addInfo = getUserById(id).getJSONObject("additionalInfo");

        if (addInfo.isEmpty()) {
            JSONArray subscribedBidsArr = new JSONArray();
            subscribedBidsArr.put(bidId);

            addInfo.put("subscribedBids", subscribedBidsArr);
            userAPI.updatePartialById(id, userAPI.parseToJsonForPartialUpdate(addInfo));

        } else {
            boolean subcribed = false;
            for (int i=0; i<addInfo.getJSONArray("subscribedBids").length(); i++) {
                if (addInfo.getJSONArray("subscribedBids").get(i).equals(bidId)) {
                    subcribed = true;
                }
            }

            if (!subcribed) {
                addInfo.getJSONArray("subscribedBids").put(bidId);
                userAPI.updatePartialById(id, userAPI.parseToJsonForPartialUpdate(addInfo));
            } else {
                System.out.println("Already subscribed to this bid");
            }
        }
        System.out.println(addInfo);
    }

    /**
     * Get tutor's subscribed open bids.
     * @param id tutor's user id
     * @return
     */
    public static ArrayList<OpenBid> getSubscribedBids(String id) {
        JSONObject addInfo = getUserById(id).getJSONObject("additionalInfo");
        ArrayList<OpenBid> retVal = new ArrayList<>();

        if (addInfo.isEmpty()) {
            return retVal;
        }

        for ( int i=0; i < addInfo.getJSONArray("subscribedBids").length(); i++ ) {
            retVal.add((OpenBid) getBidById(addInfo.getJSONArray("subscribedBids").getString(i)));
        }

        return retVal;
    }

    public static JSONObject userLogin(String username, String password) {
        return userAPI.userLogin(username, password);
    }

    public static ArrayList<Competency> getUserCompetenciesById(String id) {
        JSONObject response = (JSONObject) userAPI.getById(id, Constant.COMPETENCIES_SUBJECT);
        JSONArray arr = response.getJSONArray("competencies");

        ArrayList<Competency> retVal = new ArrayList<>();

        if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
                try {
                    JSONObject obj = arr.getJSONObject(i);
                    retVal.add(objectMapper.readValue(obj.toString(), Competency.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        return retVal;
    }

    public static ArrayList<Qualification> getUserQualificationsById(String id) {
        JSONObject response = (JSONObject) userAPI.getById(id, Constant.QUALIFICATIONS);
        JSONArray arr = response.getJSONArray("qualifications");

        ArrayList<Qualification> retVal = new ArrayList<>();

        // Update the list of qualifications to UserCookie
        if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
                try {
                    JSONObject obj = arr.getJSONObject(i);
                    retVal.add(objectMapper.readValue(obj.toString(), Qualification.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        return retVal;
    }

    public static Bid getUserInitiatedBidById(String id) {
        JSONObject response = (JSONObject) userAPI.getById(id, Constant.INITIATEDBIDS);
        JSONArray arr = response.getJSONArray("initiatedBids");
        // get the active bid
        if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
                if(arr.getJSONObject(i).isNull("dateClosedDown")){
                    BidFactory bidFactory = new BidFactory();
                    System.out.println(arr.getJSONObject(i));
                    return bidFactory.createBid(arr.getJSONObject(i));
                }
            }
        }
        return null;
    }


    // Qualification APIs
    public static ArrayList<Qualification> getAllQualifications() {
        return (ArrayList<Qualification>) qualificationAPI.getAll();
    }

    // Bid APIs
    public static ArrayList<Bid> getAllBids() {
        return (ArrayList<Bid>) bidAPI.getAll();
    }

    public static Bid getBidById(String id) {
        return (Bid) bidAPI.getById(id, Constant.NONE);
    }

    public static Bid createBid(String type, String id, String subjectId, JSONObject addtionalInfo) {
        return (Bid) bidAPI.create(bidAPI.parseToJsonForCreate(
                type,
                id,
                subjectId,
                addtionalInfo
        ));
    }

    public static void updateBidById(String id, JSONObject requestBody) {
        bidAPI.updatePartialById(id, bidAPI.parseToJsonForPartialUpdate(requestBody));
    }

    public static void closeDownBidById(String id, Timestamp closeDownTime) {
        bidAPI.closeDownBidById(id, closeDownTime);
    }

    // Message APIs
    public static Message getMessageById(String id) {
        return (Message) messageAPI.getById(id, Constant.NONE);
    }

    public static Message createMessage(String bidId, String posterId, String content) {
        return (Message) messageAPI.create(messageAPI.parseToJsonForCreate(
                bidId,
                posterId,
                content
        ));
    }

    public static void updateMessageById(String id, String content, JSONObject requestBody) {
        messageAPI.updatePartialById(id, messageAPI.parseToJsonForPartialUpdate(content, requestBody));
    }

    // Contract APIs
    // only Tutor need to call this
    public static boolean signContractById(String id) {
        return contractAPI.sign(id);
    }

    // only a Student can initiate a contract
    public static Contract createContract(String studentId, String tutorId, String subjectId, Timestamp expiry,
                                          JSONObject paymentInfo,
                                          JSONObject lessonInfo, JSONObject additionalInfo){
        return (Contract) contractAPI.create(contractAPI.parseToJson(studentId,tutorId,
                subjectId, expiry, paymentInfo, lessonInfo, additionalInfo));
    }

    public static ArrayList<Contract> getContractsByUserId(String userId) {
        ArrayList<Contract> contracts = (ArrayList<Contract>) contractAPI.getAll();
        ArrayList<Contract> retVal = new ArrayList<>();

        if (contracts != null) {
            for (Contract c: contracts) {
                if (c.getFirstParty().getId().equals(userId)) {
                    retVal.add(c);
                } else if (c.getSecondParty().getId().equals(userId)) {
                    retVal.add(c);
                }
            }
        }

        return retVal;
    }
}
