package com.matchingSystem.API;

import com.matchingSystem.API.APIAdapters.*;
import com.matchingSystem.API.ClientInterfaces.*;

public class APIFacade {
    private static APIFacade ourInstance;
    private static final ContractAPIInterface contractAPI = ContractAPI.getInstance();
    private static final BidAPIInterface bidAPI = BidAPI.getInstance();
    private static final MessageAPIInterface messageAPI = MessageAPI.getInstance();
    private static final UserAPIInterface userAPI = UserAPI.getInstance();
    private static final SubjectAPIInterface subjectAPI = SubjectAPI.getInstance();
    private static final CompetencyAPIInterface competencyAPI = CompetencyAPI.getInstance();
    private static final QualificationAPIInterface qualificationAPI = QualificationAPI.getInstance();

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

    public static BidAPIInterface getBidAPI() {
        return bidAPI;
    }

    public static MessageAPIInterface getMessageAPI() {
        return messageAPI;
    }

    public static UserAPIInterface getUserAPI() {
        return userAPI;
    }

    public static SubjectAPIInterface getSubjectAPI() {
        return subjectAPI;
    }

    public static CompetencyAPIInterface getCompetencyAPI() {
        return competencyAPI;
    }

    public static QualificationAPIInterface getQualificationAPI() {
        return qualificationAPI;
    }
}
