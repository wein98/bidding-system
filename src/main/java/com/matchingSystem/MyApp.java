package com.matchingSystem;

import com.matchingSystem.UI.TestingForm;

import javax.swing.*;
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

        JFrame frame = new JFrame("Testing");
        frame.setContentPane(new TestingForm().TestingFormPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
