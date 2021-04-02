package com.matchingSystem;

import com.matchingSystem.API.MessageAPI;
import com.matchingSystem.UI.LoginForm;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyApp {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Note: Change the message id in testDelete before running this
        testMessage();
//        JFrame frame = new JFrame("Testing");
//        frame.setContentPane(new LoginForm().loginForm);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
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
}
