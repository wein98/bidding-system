package com.matchingSystem.LoginSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Constant;

public class UserFactory {
    public User createUser(String userInfo, int userType) {
        ObjectMapper objMapper = new ObjectMapper();
//        JSONObject jsonObject = new JSONObject(userInfo);

        if (userType == Constant.IS_STUDENT) {
            try {
                return objMapper.readValue(userInfo, Student.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } else if (userType == Constant.IS_TUTOR) {
            try {
                return objMapper.readValue(userInfo, Tutor.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
