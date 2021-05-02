package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.ContractAPIInterface;
import com.matchingSystem.ContractDev.Contract;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static com.matchingSystem.API.APIService.*;

public class ContractAPI extends APIRouter implements ContractAPIInterface {
    public ContractAPI() {
        this.objMapper = new ObjectMapper();
        route = "/contract";
        c = Contract.class;
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
//                System.out.println(jsonObj);
                Object dateSigned = jsonObj.get("dateSigned");
                String dateSignedString = dateSigned.toString();
                jsonObj.remove("dateSigned");
//                System.out.println(jsonObj);
                String jsonStr = jsonObj.toString();
                Contract contract = objMapper.readValue(jsonStr, Contract.class);
                if (!dateSignedString.equals("null")) {
                    Date d = Utility.sdf2.parse(dateSignedString);
                    Timestamp t = new Timestamp(d.getTime());
                    contract.setDateSigned(t);
                }
                contracts.add(contract);
            }

            return contracts;
        }catch (Exception e){
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
    @Override
    public StringBuilder parseToJson(String firstPartyId, String secondPartyId,
                                              String subjectId, Timestamp expiryDate,
                                              JSONObject paymentInfo, JSONObject lessonInfo,
                                              JSONObject additionalInfo) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"firstPartyId\": \"%s\",", firstPartyId));
        jsonParam.append(String.format("\"secondPartyId\": \"%s\",", secondPartyId));
        jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
        jsonParam.append(String.format("\"dateCreated\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"expiryDate\": \"%s\",", Utility.sdf2.format(expiryDate)));
        jsonParam.append("\"paymentInfo\": ");
        jsonParam.append(paymentInfo);
        jsonParam.append(",");
        jsonParam.append("\"lessonInfo\": ");
        jsonParam.append(lessonInfo);
        jsonParam.append(",");
        jsonParam.append("\"additionalInfo\": ");
        jsonParam.append(additionalInfo);
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
            System.out.println(response);
            JSONObject resObj = new JSONObject(response);
            System.out.println(resObj.toString());
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
