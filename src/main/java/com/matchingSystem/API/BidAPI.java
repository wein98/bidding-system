package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIAdapters.APIRouter;
import com.matchingSystem.Model.Bid;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import static com.matchingSystem.API.APIService.*;

public class BidAPI extends APIRouter {

    ObjectMapper objMapper = new ObjectMapper();
    private static final String APIPATH = "/bid";

    public ArrayList<Bid> getAll(){
        ArrayList<Bid> bids = new ArrayList<>();
        try {
            String response = GETRequest(APIPATH);
//            response = response.replace("[","");
//            response = response.replace("]","");
//            response = response.replace("},{","}  {");
//            if(response.length() > 0) {
//                String[] resArr = response.split("  ", 0);
//                for (int i = 0; i < resArr.length; i++) {
//                    Bid bid = objMapper.readValue(resArr[i], Bid.class);
//                    bids.add(bid);
//                }
//            }
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Bid bid = objMapper.readValue(jsonObj.toString(), Bid.class);
                bids.add(bid);
            }
            return bids;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public Bid create(String type, String initiatorId, String subjectId){
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"type\": \"%s\",", type));
            jsonParam.append(String.format("\"initiatorId\": \"%s\",", initiatorId));
            jsonParam.append(String.format("\"datePosted\": \"%s\",", Utility.sdf2.format(now)));
            jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
            jsonParam.append(String.format("\"additionalInfo\": {}"));
            jsonParam.append("}");
            String response = UpdateRequest(APIPATH, jsonParam, APIService.POST);
            Bid bid = objMapper.readValue(response, Bid.class);

            return bid;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public Bid getById(String id){
        try {
            String route = APIPATH + "/" + id;
            String response = GETRequest(route);
            Bid bid = objMapper.readValue(response, Bid.class);
            return bid;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public Bid updatePartialById(String id, String content){
        try{
            String route = APIPATH + "/" + id;

            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"additionalInfo\": {}"));
            jsonParam.append("}");

            String response = UpdateRequest(route, jsonParam, APIService.PATCH);
            Bid bid = objMapper.readValue(response, Bid.class);

            return bid;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean closeDownBidById(String id){
        try {
            String route = APIPATH + "/" + id + "/close-down";
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"dateClosedDown\": \"%s\"", Utility.sdf2.format(now)));
            jsonParam.append("}");
//            System.out.println(jsonParam);
            String response = UpdateRequest(route, jsonParam, APIService.POST);
//            System.out.println(response);
            JSONObject resObj = new JSONObject(response);
//            System.out.println(resObj);
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
