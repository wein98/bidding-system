package com.matchingSystem;

import org.json.JSONObject;

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
}
