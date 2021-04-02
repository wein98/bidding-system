package com.matchingSystem;

import org.json.JSONObject;

import java.lang.Object;
public interface APIBehaviour {
    Object getAll();
    Object create(String itemId, String creatorId);
    Object getById(String id);
    boolean deleteById(String id);
    Object updatePartialById(String id);
}
