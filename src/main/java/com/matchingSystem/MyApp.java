package com.matchingSystem;

import com.matchingSystem.API.APIAdapters.*;
import com.matchingSystem.API.ClientInterfaces.CompetencyAPIInterface;
import com.matchingSystem.API.ClientInterfaces.QualificationAPIInterface;
import com.matchingSystem.API.ClientInterfaces.SubjectAPIInterface;
import com.matchingSystem.API.MessageAPI;
import com.matchingSystem.Model.Competency;
import com.matchingSystem.Model.Message;
import com.matchingSystem.Model.Qualification;
import com.matchingSystem.Model.Subject;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyApp {
    public static final String USER_ID = "f5449b1f-6f55-408a-88ee-194958f52219";
    public static final String COMPETENCY_ID = "36502d02-9f07-4394-af77-9a5489570f82";
    public static final String QUALIFICATION_ID = "657c69ae-c7ad-4a6c-b13a-6bb024346d19";
    public static final String SUBJECT_ID = "148e0af0-699b-4c1f-9e49-4de8816d121e";

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Note: Change the message id in testDelete before running this
//        testMessage();
        // testMessage();
//        BidAPI bidApi =  new BidAPI();
//        boolean res = bidApi.closeDownBidById("bc06e9ad-5d20-4dce-a176-a6ac73b26b35");
//        JFrame frame = new JFrame("Testing");
//        frame.setContentPane(new LoginForm().loginForm);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

//        testSubjectAPI();
        testCompetencyAPI();
//        testQualification();
//        testUserAPI();
    }

    /**
     * Just change the API class to test for different APIs
     */
    public static void testGetAll(){
        MessageAPI smtg = new MessageAPI();
        ArrayList<Message> messages = smtg.getAll();
        for (Message message:messages) {
            System.out.println(message.toString()+"\n");
        }
    }

    public static void testCreate(){
        MessageAPI smtg = new MessageAPI();
        Message newMessage = smtg.create("bc06e9ad-5d20-4dce-a176-a6ac73b26b35","ecc52cc1-a3e4-4037-a80f-62d3799645f4","New Stuff 4");
    }

    public static void testDelete(){
        MessageAPI smtg = new MessageAPI();
        // NOTE: Change this message id to a valid one before running testMessage()
        boolean delStatus = smtg.deleteById("9a75a3de-0840-4a95-818b-1802c31899d2");
        if (delStatus){
            System.out.println("deleted successfully");
        }else {
            System.out.println("unsuccessful in deleting the message");
        }
    }

    public static void testPatch(){
        MessageAPI smtg = new MessageAPI();
        Message toUpdate = smtg.updatePartialById("f66ff397-3132-453d-8ef6-cfdbc25230b6","Some Very Old Things");
    }

    public static void testMessage(){
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
//        Subject subject = subjectAPI.getById("148e0af0-699b-4c1f-9e49-4de8816d121e");
//        System.out.println(subject.toString());
//    }

    public static void testUserAPI() {
        String username = "mbrown123";

        UserAPI userAPI = new UserAPI();

        JSONObject response = userAPI.userLogin(username, username);

        Utility.decodeJWT(response.getString("jwt"));
    }

    public static void testCompetencyAPI() {
        System.out.println("===== Testing CompetencyAPI =====");
        CompetencyAPIInterface competencyAPI = new CompetencyAPI();
        Competency competency;
        String competencyId;

        System.out.println("\ncreate()");
        StringBuilder createParams = competencyAPI.parseToJsonForCreate(USER_ID, SUBJECT_ID, 0);
        competency = (Competency) competencyAPI.create(createParams);
        System.out.println(competency.toString());
        competencyId = competency.getId();

        System.out.println("\ngetById()");
        competency = (Competency) competencyAPI.getById(competencyId);
        System.out.println(competency.toString());

        System.out.println("\nupdatePartialById()");
        StringBuilder partialUpdateParams = competencyAPI.parseToJsonForPartialUpdate(0);
        competency = (Competency) competencyAPI.updatePartialById(competencyId, partialUpdateParams);
        System.out.println(competency.toString());

        System.out.println("\ndeleteById()");
        boolean delete = competencyAPI.deleteById(competencyId);
        System.out.println(delete);
    }

    public static void testSubjectAPI() {
        System.out.println("===== Testing SubjectAPI =====");
        SubjectAPIInterface subjectAPI = new SubjectAPI();
        Subject subject;
        String subjectId;

        System.out.println("\ncreate()");
        StringBuilder createParams = subjectAPI.parseToJson("Testing Subject", "Testing");
        subject = (Subject) subjectAPI.create(createParams);
        System.out.println(subject.toString());
        subjectId = subject.getId();

        System.out.println("\ngetById()");
        subject = (Subject) subjectAPI.getById(subjectId);
        System.out.println(subject.toString());

        System.out.println("\nupdatePartialById()");
        StringBuilder partialUpdateParams = subjectAPI.parseToJson("Testing Subject", "Testing");
        subject = (Subject) subjectAPI.updatePartialById(subjectId, partialUpdateParams);
        System.out.println(subject.toString());

        System.out.println("\ndeleteById()");
        boolean delete = subjectAPI.deleteById(subjectId);
        System.out.println(delete);
    }

    public static void testQualification() {
        System.out.println("===== Testing QualificationAPI =====");
        QualificationAPIInterface qualificationAPI = new QualificationAPI();
        Qualification qualification;
        String qualificationId;

        System.out.println("\ncreate()");
        StringBuilder createParams = qualificationAPI.parseToJson("Testing Qualification", "testing", "true", USER_ID);
        qualification = (Qualification) qualificationAPI.create(createParams);
        System.out.println(qualification.toString());
        qualificationId = qualification.getId();

        System.out.println("\ngetById()");
        qualification = (Qualification) qualificationAPI.getById(qualificationId);
        System.out.println(qualification.toString());

        System.out.println("\nupdatePartialById()");
        StringBuilder partialUpdateParams = qualificationAPI.parseToJson("Testing Qualification Update", "testing", "true", USER_ID);
        qualification = (Qualification) qualificationAPI.updatePartialById(qualificationId, partialUpdateParams);
        System.out.println(qualification.toString());

        System.out.println("\ndeleteById()");
        boolean delete = qualificationAPI.deleteById(qualificationId);
        System.out.println(delete);
    }
}
