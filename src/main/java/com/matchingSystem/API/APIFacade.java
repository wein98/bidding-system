package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIAdapters.*;
import com.matchingSystem.API.ClientInterfaces.*;
import com.matchingSystem.Constant;
import com.matchingSystem.Model.*;
import com.matchingSystem.UserCookie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class APIFacade {
    private static APIFacade ourInstance;
    private static final ContractAPIInterface contractAPI = ContractAPI.getInstance();
    private static final BidAPIInterface bidAPI = BidAPI.getInstance();
    private static final MessageAPIInterface messageAPI = MessageAPI.getInstance();
    private static final UserAPIInterface userAPI = UserAPI.getInstance();
    private static final SubjectAPIInterface subjectAPI = SubjectAPI.getInstance();
    private static final CompetencyAPIInterface competencyAPI = CompetencyAPI.getInstance();
    private static final QualificationAPIInterface qualificationAPI = QualificationAPI.getInstance();

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
    public static User getUserById(String id) {
        return (User) userAPI.getById(id, Constant.NONE);
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
                if(arr.getJSONObject(i).get("dateClosedDown") == null){
                    BidFactory bidFactory = new BidFactory();
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
        return (Contract) contractAPI.create(contractAPI.parseToJsonForCreate(studentId,tutorId,
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
