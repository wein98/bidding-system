package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.BidAPIInterface;
import com.matchingSystem.Model.*;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import static com.matchingSystem.API.APIService.*;

public class BidAPI extends APIRouter implements BidAPIInterface {
    /**
     * Singleton design pattern
     */
    private static BidAPI ourInstance;
    /**
     * BidAPI constructor (private)
     */
    private BidAPI() {
        this.objMapper = new ObjectMapper();
        route = "/bid";
        this.c = OpenBid.class;
    }

    /**
     * Global access point
     * @return the only instance of this class
     */
    public static BidAPI getInstance() {

        if (ourInstance == null) {
            ourInstance = new BidAPI();
        }
        return ourInstance;
    }

    /**
     * Get all bid requests
     * @return an array of Bids
     */
    @Override
    public ArrayList<Bid> getAll(){
        ArrayList<Bid> bids = new ArrayList<>();
            String response = GETRequest(this.route);
            JSONArray arr = new JSONArray(response);
            BidFactory bidFactory = new BidFactory();

            for (int i = 0; i < arr.length(); i++) {
                Bid bid = bidFactory.createBid(arr.getJSONObject(i));
                bids.add(bid);
            }
            return bids;
    }

    /**
     * Function that parses variables to json needed for the request body for create()
     * @param type type of bidding (open or close)
     * @param initiatorId Id of student who posted the bid request
     * @param subjectId Id of the subject requested
     * @param additionalInfo additional infomation about this bid request
     * @return StringBuilder for the parsed json
     */
    public StringBuilder parseToJsonForCreate(String type, String initiatorId, String subjectId, JSONObject additionalInfo){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"type\": \"%s\",", type));
        jsonParam.append(String.format("\"initiatorId\": \"%s\",", initiatorId));
        jsonParam.append(String.format("\"dateCreated\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
        jsonParam.append("\"additionalInfo\": ");
        jsonParam.append(additionalInfo);
        jsonParam.append("}");

        return jsonParam;
    }

    /**
     * Function that parses variables to json needed for the request body for updatePartialById()
     * @param additionalInfo additional information to update
     * @return StringBuilder of the parsed json
     */
    public StringBuilder parseToJsonForPartialUpdate(JSONObject additionalInfo){
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"additionalInfo\": \"%s\"", additionalInfo.toString()));
        jsonParam.append("}");

        return jsonParam;
    }

    /**
     * Function to call the API to closed down a Bid
     * @param id Id of the bid
     * @return true if bid request is successfully closed down
     */
    public boolean closeDownBidById(String id){
        try {
            String route = this.route + "/" + id + "/close-down";
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"dateClosedDown\": \"%s\"", Utility.sdf2.format(now)));
            jsonParam.append("}");
            String response = UpdateRequest(route, jsonParam, APIService.POST);
            JSONObject resObj = new JSONObject(response);
            // TODO: add conditions for different status code (400, 401, 409)
            if (resObj.getInt("statusCode") == 200){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
