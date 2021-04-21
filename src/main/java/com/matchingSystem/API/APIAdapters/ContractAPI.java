package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.ContractAPIInterface;
import com.matchingSystem.Model.Contract;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import static com.matchingSystem.API.APIService.*;

public class ContractAPI extends APIRouter implements ContractAPIInterface {

    public ContractAPI() {
        ObjectMapper objMapper = new ObjectMapper();
        route = "/contract";
        c = Contract.class;
    }
    public ArrayList<Contract> getAll(){
        ArrayList<Contract> contracts = new ArrayList<>();
        try {
            String response = GETRequest(this.route);
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Contract contract = objMapper.readValue(jsonObj.toString(), Contract.class);
                contracts.add(contract);
            }

            return contracts;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public StringBuilder parseToJsonForCreate(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate){
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

        return jsonParam;
    }

    public StringBuilder parseToJsonForPartialUpdate(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate) {
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

        return jsonParam;
    }

    public boolean sign(String id){
        try {
            String route = this.route + "/" + id + "/sign";
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"dateSigned\": \"%s\"", Utility.sdf2.format(now)));
            jsonParam.append("}");
            String response = UpdateRequest(route, jsonParam, APIService.POST);
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
