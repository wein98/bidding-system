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

    public void initiateContract(String studentId, String tutorId, String subjectId, JSONObject paymentInfo,
                                 JSONObject lessonInfo, JSONObject additionalInfo) {
        int months = Integer.parseInt(this.contractDuration);
        LocalDateTime expiryDuration = LocalDate.now().plusMonths(months).atTime(0, 0);
        Timestamp expiry = Timestamp.valueOf(expiryDuration);
        additionalInfo.put("duration", this.contractDuration);
        APIFacade.createContract(studentId, tutorId, subjectId, expiry, paymentInfo, lessonInfo, additionalInfo);
    }
}
