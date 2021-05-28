package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Bid;
import com.matchingSystem.ContractDev.Contract;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContractCreationModel {
    private String contractDuration = "6"; // default is 6 months

    public void setContractDuration(String month) {
        this.contractDuration = month;
    }

    public void initiateContract(JSONObject details) {
        int months = Integer.parseInt(this.contractDuration);
        LocalDateTime expiryDuration = LocalDate.now().plusMonths(months).atTime(0, 0);
        Timestamp expiry = Timestamp.valueOf(expiryDuration);
        JSONObject addInfo = details.getJSONObject("addInfo");
        addInfo.put("duration", this.contractDuration);
        APIFacade.createContract(details.getString("studentId"), details.getString("tutorId"), details.getString("subjectId"), expiry, details.getJSONObject("payInfo"),
                details.getJSONObject("lessInfo"), addInfo);
    }
}
