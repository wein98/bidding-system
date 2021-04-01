package com.matchingSystem;

import java.util.ArrayList;

public class MyApp {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        // try out MessageAPI.getAll()
        MessageAPI smtg = new MessageAPI();
        ArrayList<Message> messages = smtg.getAll();
        for (Message message:messages) {
            System.out.println(message.toString()+"\n");
        }
    }
}
