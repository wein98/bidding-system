package com.matchingSystem.API;

import org.json.JSONObject;

import java.lang.Object;
public interface APIBehaviour {
    Object getAll();
//    Object create();
    Object getById(String id);
    boolean deleteById(String id); //
//    Object updatePartialById(String id, String payload); // TODO: different obj need different parameters ... should we just give it as a String ? or StringBuilder
}
