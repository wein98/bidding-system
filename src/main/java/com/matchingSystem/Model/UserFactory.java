package com.matchingSystem.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class UserFactory {
    public final static int IS_STUDENT = 0;
    public final static int IS_TUTOR = 1;

    public User createUser(String userInfo, int userType) {
        ObjectMapper objMapper = new ObjectMapper();
//        JSONObject jsonObject = new JSONObject(userInfo);

        if (userType == IS_STUDENT) {
            try {
                return objMapper.readValue(userInfo, Student.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } else if (userType == IS_TUTOR) {
            try {
                return objMapper.readValue(userInfo, Tutor.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
