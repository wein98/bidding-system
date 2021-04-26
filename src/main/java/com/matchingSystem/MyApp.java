package com.matchingSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIAdapters.*;
import com.matchingSystem.API.ClientInterfaces.CompetencyAPIInterface;
import com.matchingSystem.API.ClientInterfaces.ContractAPIInterface;
import com.matchingSystem.API.ClientInterfaces.QualificationAPIInterface;
import com.matchingSystem.API.ClientInterfaces.SubjectAPIInterface;
import com.matchingSystem.API.APIAdapters.MessageAPI;
import com.matchingSystem.Model.*;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyApp {
    public static final String USER_ID = "f5449b1f-6f55-408a-88ee" + "-194958f52219";
    public static final String COMPETENCY_ID = "36502d02-9f07-4394-af77" +
            "-9a5489570f82";
    public static final String QUALIFICATION_ID = "657c69ae-c7ad-4a6c" + "-b13a" +
            "-6bb024346d19";
    public static final String SUBJECT_ID = "148e0af0-699b-4c1f-9e49" +
            "-4de8816d121e";

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Note: Change the message id in testDelete before running this
//        testMessage();
        // testMessage();
//        BidAPI bidApi =  new BidAPI();
//        boolean res = bidApi.closeDownBidById
//        ("bc06e9ad-5d20-4dce-a176-a6ac73b26b35");
//        JFrame frame = new JFrame("Testing");
//        frame.setContentPane(new LoginForm().loginForm);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

//        testSubjectAPI();
//        testCompetencyAPI();
//        testQualification();
//        testUserAPI();
        try {
            testContract();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Just change the API class to test for different APIs
     */
    public static void testGetAll() {
        MessageAPI smtg = MessageAPI.getInstance();
        ArrayList<Message> messages = smtg.getAll();
        for (Message message : messages) {
            System.out.println(message.toString() + "\n");
        }
    }

    public static void testCreate() {
        MessageAPI smtg = MessageAPI.getInstance();
//        Message newMessage = smtg.create
//        ("bc06e9ad-5d20-4dce-a176-a6ac73b26b35",
//        "ecc52cc1-a3e4-4037-a80f-62d3799645f4","New Stuff 4");
    }

    public static void testDelete() {
        MessageAPI smtg = MessageAPI.getInstance();
        // NOTE: Change this message id to a valid one before running
        // testMessage()
        boolean delStatus = smtg.deleteById("9a75a3de-0840-4a95-818b" +
                "-1802c31899d2");
        if (delStatus) {
            System.out.println("deleted successfully");
        } else {
            System.out.println("unsuccessful in deleting the message");
        }
    }

    public static void testPatch() {
        MessageAPI smtg = MessageAPI.getInstance();
//        Message toUpdate = smtg.updatePartialById
//        ("f66ff397-3132-453d-8ef6-cfdbc25230b6","Some Very Old Things");
    }

    public static void testMessage() {
        System.out.println("Before any action");
        testGetAll();

        System.out.println("Create a new item");
        testCreate();
        testGetAll();

        System.out.println("Delete an item");
        testDelete();
        testGetAll();

        System.out.println("Test updating (patching) an item");
        testPatch();
        testGetAll();
    }

//    public static void testSubject() {
//        SubjectAPI subjectAPI = new SubjectAPI();
//
//        Subject subject = subjectAPI.getById
//        ("148e0af0-699b-4c1f-9e49-4de8816d121e");
//        System.out.println(subject.toString());
//    }

    public static void testUserAPI() {
        String username = "mbrown123";

        UserAPI userAPI = UserAPI.getInstance();

        JSONObject response = userAPI.userLogin(username, username);

        Utility.decodeJWT(response.getString("jwt"));
    }

    public static void testCompetencyAPI() {
        System.out.println("===== Testing CompetencyAPI =====");
        CompetencyAPIInterface competencyAPI = CompetencyAPI.getInstance();
        Competency competency;
        String competencyId;

        System.out.println("\ncreate()");
        StringBuilder createParams = competencyAPI.parseToJsonForCreate(USER_ID,
                SUBJECT_ID, 0);
        competency = (Competency) competencyAPI.create(createParams);
        System.out.println(competency.toString());
        competencyId = competency.getId();

        System.out.println("\ngetById()");
//        competency = (Competency) competencyAPI.getById(competencyId);
        System.out.println(competency.toString());

        System.out.println("\nupdatePartialById()");
        StringBuilder partialUpdateParams =
                competencyAPI.parseToJsonForPartialUpdate(0);
        competency = (Competency) competencyAPI.updatePartialById(competencyId,
                partialUpdateParams);
        System.out.println(competency.toString());

        System.out.println("\ndeleteById()");
        boolean delete = competencyAPI.deleteById(competencyId);
        System.out.println(delete);
    }

    public static void testSubjectAPI() {
        System.out.println("===== Testing SubjectAPI =====");
        SubjectAPIInterface subjectAPI = SubjectAPI.getInstance();
        Subject subject;
        String subjectId;

        System.out.println("\ncreate()");
        StringBuilder createParams = subjectAPI.parseToJson("Testing " + "Subject"
                , "Testing");
        subject = (Subject) subjectAPI.create(createParams);
        System.out.println(subject.toString());
        subjectId = subject.getId();

        System.out.println("\ngetById()");
//        subject = (Subject) subjectAPI.getById(subjectId);
        System.out.println(subject.toString());

        System.out.println("\nupdatePartialById()");
        StringBuilder partialUpdateParams = subjectAPI.parseToJson("Testing " +
                "Subject", "Testing");
        subject = (Subject) subjectAPI.updatePartialById(subjectId,
                partialUpdateParams);
        System.out.println(subject.toString());

        System.out.println("\ndeleteById()");
        boolean delete = subjectAPI.deleteById(subjectId);
        System.out.println(delete);
    }

    public static void testQualification() {
        System.out.println("===== Testing QualificationAPI =====");
        QualificationAPIInterface qualificationAPI = QualificationAPI.getInstance();
        Qualification qualification;
        String qualificationId;

        System.out.println("\ncreate()");
        StringBuilder createParams = qualificationAPI.parseToJson("Testing " +
                "Qualification", "testing", "true", USER_ID);
        qualification = (Qualification) qualificationAPI.create(createParams);
        System.out.println(qualification.toString());
        qualificationId = qualification.getId();

        System.out.println("\ngetById()");
//        qualification = (Qualification) qualificationAPI.getById
//        (qualificationId);
        System.out.println(qualification.toString());

        System.out.println("\nupdatePartialById()");
        StringBuilder partialUpdateParams = qualificationAPI.parseToJson("Testing " +
                "Qualification " + "Update", "testing", "true", USER_ID);
        qualification =
                (Qualification) qualificationAPI.updatePartialById(qualificationId
                        , partialUpdateParams);
        System.out.println(qualification.toString());

        System.out.println("\ndeleteById()");
        boolean delete = qualificationAPI.deleteById(qualificationId);
        System.out.println(delete);
    }

    public static void testContract() {
        try {
            System.out.println("===== Testing ContractAPI =====");
            ContractAPIInterface contractAPI = ContractAPI.getInstance();
            Contract contract;
            String contractId;

            System.out.println("\ngetAll()");
            ArrayList<Contract> contracts = ((ContractAPI) contractAPI).getAll();
            System.out.println("--> Successful");

            System.out.println("\ncreate()");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("23/09/2021");
            long time = date.getTime();
            JSONObject lessonInfo1 = new JSONObject();
            lessonInfo1.put("time","3");
            lessonInfo1.put("dayNight","PM");
            lessonInfo1.put("prefDay","Monday");
            lessonInfo1.put("numOfLesson","3");
            JSONObject addInfo1 = new JSONObject();
            addInfo1.put("rate","333333");
            StringBuilder createParams = contractAPI.parseToJsonForCreate(
                    "bb495c2c-3e30-4e4a-80e7-3954c448d128",
                    "984e7871-ed81-4f75-9524-3d1870788b1f",
                    "148e0af0-699b-4c1f-9e49-4de8816d121e", new Timestamp(time),
                    new JSONObject(), lessonInfo1, addInfo1);
            contract = (Contract) contractAPI.create(createParams);
            contractId = contract.getId();
            System.out.println("--> Successful");

            System.out.println("\ngetById()");
            Contract contract2 = (Contract) contractAPI.getById(contractId, Constant.NONE_S);
            System.out.println("--> Successful");

            System.out.println("\nupdatePartialById()");
            JSONObject lessonInfo2 = new JSONObject();
            lessonInfo2.put("time","5");
            lessonInfo2.put("dayNight","PM");
            lessonInfo2.put("prefDay","Tuesday");
            lessonInfo2.put("numOfLesson","3");
            JSONObject addInfo2 = new JSONObject();
            addInfo2.put("rate","33333553");
            StringBuilder updateParams = contractAPI.parseToJsonForPartialUpdate(
                    "bb495c2c-3e30-4e4a-80e7-3954c448d128",
                    "984e7871-ed81-4f75-9524-3d1870788b1f",
                    "148e0af0-699b-4c1f-9e49-4de8816d121e", new Timestamp(time),
                    new JSONObject(), lessonInfo2, addInfo2);
            Contract contract3 = (Contract) contractAPI.updatePartialById(contractId,updateParams);
            System.out.println("--> Successful");

            System.out.println("\ndeleteById()");
            boolean deleted = contractAPI.deleteById(contractId);
            System.out.println(deleted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
