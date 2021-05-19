package com.matchingSystem;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Base64;

public class Utility {

    public static JSONObject decodeJWT(String jwt) {
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println(payload);
        return new JSONObject(payload);
    }

    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static final String[] timeVals = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    public static final String[] dayNight = {"AM", "PM"};
    public static final String[] duration = {"1", "1.5", "2", "2.5", "3", "3.5"};
    public static final String[] contractDuration = {"3","6","12","24","48"};
    public static final String[] numsForLesson = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
}
