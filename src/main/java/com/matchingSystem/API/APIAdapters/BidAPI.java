package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.BidAPIInterface;
import com.matchingSystem.Model.Bid;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import static com.matchingSystem.API.APIService.*;

public class BidAPI extends APIRouter implements BidAPIInterface {

    /**
     * BidAPI constructor
     */
    public BidAPI() {
        this.objMapper = new ObjectMapper();
        route = "/bid";
        this.c = Bid.class;
    }

    @Override
    public ArrayList<Bid> getAll(){
        ArrayList<Bid> bids = new ArrayList<>();
        try {
            String response = GETRequest(this.route);
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                String jsonStr =  jsonObj.toString();
                Bid bid = objMapper.readValue(jsonStr, Bid.class);
                bids.add(bid);
            }
            return bids;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public StringBuilder parseToJsonForCreate(String type, String initiatorId, String subjectId, JSONObject additionalInfo){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"type\": \"%s\",", type));
        jsonParam.append(String.format("\"initiatorId\": \"%s\",", initiatorId));
        jsonParam.append(String.format("\"datePosted\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
        jsonParam.append(String.format("\"additionalInfo\": \"%s\"", additionalInfo.toString()));
        jsonParam.append("}");

        return jsonParam;
    }

    public StringBuilder parseToJsonForPartialUpdate(JSONObject additionalInfo){
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"additionalInfo\": \"%s\"", additionalInfo.toString()));
        jsonParam.append("}");

        return jsonParam;
    }

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
