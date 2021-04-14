package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Model.Contract;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import static com.matchingSystem.API.APIManager.*;

public class ContractAPI extends APIRouter {
    ObjectMapper objMapper = new ObjectMapper();
    private static final String APIPATH = "/contract";

    public ArrayList<Contract> getAll(){
        ArrayList<Contract> contracts = new ArrayList<>();
        try {
            String response = GETRequest(APIPATH);
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Contract contract = objMapper.readValue(jsonObj.toString(), Contract.class);
                contracts.add(contract);
            }
//            response = response.replace("[","");
//            response = response.replace("]","");
//            response = response.replace("},{","}  {");
//            if(response.length() > 0) {
//                String[] resArr = response.split("  ", 0);
//                for (int i = 0; i < resArr.length; i++) {
//                    Contract contract = objMapper.readValue(resArr[i], Contract.class);
//                    contracts.add(contract);
//                }
//            }
            return contracts;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public Contract create(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate){
        try{
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"firstPartyId\": \"%s\",", firstPartyId));
            jsonParam.append(String.format("\"secondPartyId\": \"%s\",", secondPartyId));
            jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
            jsonParam.append(String.format("\"dateCreated\": \"%s\",", Utility.sdf2.format(expiryDate)));
            jsonParam.append(String.format("\"expiryDate\": \"%s\",", Utility.sdf2.format(now)));
            jsonParam.append(String.format("\"paymentInfo\": {}"));
            jsonParam.append(String.format("\"lessonInfo\": {}"));
            jsonParam.append(String.format("\"additionalInfo\": {}"));
            jsonParam.append("}");
            String response = UpdateRequest(APIPATH, jsonParam, APIManager.POST);
            Contract contract = objMapper.readValue(response, Contract.class);

            return contract;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public Contract getById(String id){
        try {
            String route = APIPATH + "/" + id;
            String response = GETRequest(route);
            Contract contract = objMapper.readValue(response, Contract.class);
            return contract;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public Contract updatePartialById(String id, String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate){
        try{
            String route = APIPATH + "/" + id;

            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"firstPartyId\": \"%s\",", firstPartyId));
            jsonParam.append(String.format("\"secondPartyId\": \"%s\",", secondPartyId));
            jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
            jsonParam.append(String.format("\"dateCreated\": \"%s\",", Utility.sdf2.format(expiryDate)));
            jsonParam.append(String.format("\"expiryDate\": \"%s\",", Utility.sdf2.format(now)));
            jsonParam.append(String.format("\"paymentInfo\": {}"));
            jsonParam.append(String.format("\"lessonInfo\": {}"));
            jsonParam.append(String.format("\"additionalInfo\": {}"));
            jsonParam.append("}");

            String response = UpdateRequest(route, jsonParam, APIManager.PATCH);
            Contract contract = objMapper.readValue(response, Contract.class);

            return contract;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean sign(String id){
        try {
            String route = APIPATH + "/" + id + "/sign";
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"dateSigned\": \"%s\"", Utility.sdf2.format(now)));
            jsonParam.append("}");
            String response = UpdateRequest(route, jsonParam, APIManager.POST);
            JSONObject resObj = new JSONObject(response);
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
