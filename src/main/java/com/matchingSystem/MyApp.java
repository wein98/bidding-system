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
        // try out MessageAPI.create()
        Message message = smtg.create("bc06e9ad-5d20-4dce-a176-a6ac73b26b35","4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9");
        System.out.println(message);
    }
}
