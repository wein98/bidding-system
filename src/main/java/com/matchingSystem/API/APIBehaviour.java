package com.matchingSystem.API;

import org.json.JSONObject;

import java.lang.Object;
public interface APIBehaviour {
    Object getAll();
    JSONObject create();
    JSONObject getById(String id);
    JSONObject deleteById(String id);
    JSONObject updatePartialById(String id);
}
