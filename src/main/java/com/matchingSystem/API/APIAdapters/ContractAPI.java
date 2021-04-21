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
    /**
     * Singleton design pattern
     */
    private static ContractAPI ourInstance;

    /**
     * ContractAPI constructor (private)
     */
    private ContractAPI() {
        ObjectMapper objMapper = new ObjectMapper();
        route = "/contract";
        c = Contract.class;
    }

    /**
     * Global access point
     * @return the only instance of this class
     */
    public static ContractAPI getInstance() {

        if (ourInstance == null) {
            ourInstance = new ContractAPI();
        }
        return ourInstance;
    }

    /**
     * Get all contracts
     * @return an array of Contracts
     */
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

    /**
     * Function that parses variables to json needed for the request body for create()
     * @param firstPartyId Id of student in the contract
     * @param secondPartyId Id of tutor in the contract
     * @param subjectId Id of the subject that is requested
     * @param expiryDate expiry of the contract
     * @return StringBuilder of parsed json
     */
    public StringBuilder parseToJsonForCreate(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate, JSONObject paymentInfo, JSONObject lessonInfo, JSONObject additionalInfo){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"firstPartyId\": \"%s\",", firstPartyId));
        jsonParam.append(String.format("\"secondPartyId\": \"%s\",", secondPartyId));
        jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
        jsonParam.append(String.format("\"dateCreated\": \"%s\",", Utility.sdf2.format(expiryDate)));
        jsonParam.append(String.format("\"expiryDate\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"paymentInfo\": \"%s\",", paymentInfo.toString()));
        jsonParam.append(String.format("\"lessonInfo\": \"%s\",", lessonInfo.toString()));
        jsonParam.append(String.format("\"additionalInfo\": \"%s\"", additionalInfo.toString()));
        jsonParam.append("}");

        return jsonParam;
    }

    /**
     * Function that parses variables to json needed for the request body for updatePartialById()
     * @param firstPartyId Id of student in the contract
     * @param secondPartyId Id of tutor in the contract
     * @param subjectId Id of subject that is requested
     * @param expiryDate expiry of the contract
     * @return StringBuilder of parsed json
     */
    public StringBuilder parseToJsonForPartialUpdate(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate, JSONObject paymentInfo, JSONObject lessonInfo, JSONObject additionalInfo) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"firstPartyId\": \"%s\",", firstPartyId));
        jsonParam.append(String.format("\"secondPartyId\": \"%s\",", secondPartyId));
        jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
        jsonParam.append(String.format("\"dateCreated\": \"%s\",", Utility.sdf2.format(expiryDate)));
        jsonParam.append(String.format("\"expiryDate\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"paymentInfo\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"paymentInfo\": \"%s\",", paymentInfo.toString()));
        jsonParam.append(String.format("\"lessonInfo\": \"%s\",", lessonInfo.toString()));
        jsonParam.append(String.format("\"additionalInfo\": \"%s\"", additionalInfo.toString()));
        jsonParam.append("}");

        return jsonParam;
    }

    /**
     * Function to call the API of signing a contract
     * @param id the id of the contract
     * @return true if signing is successful, otherwise false
     */
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
