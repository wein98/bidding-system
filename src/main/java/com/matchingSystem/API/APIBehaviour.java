package com.matchingSystem.API;

import org.json.JSONObject;

import java.lang.Object;
public interface APIBehaviour {
    Object getAll();
//    Object create();
    Object getById(String id);
    boolean deleteById(String id); // delete will return its status code, return -1 if catch error in code
//    Object updatePartialById(String id, String payload); // TODO: different obj need different parameters ... should we just give it as a String ? or StringBuilder
}
