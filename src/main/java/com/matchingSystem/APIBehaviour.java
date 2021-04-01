package com.matchingSystem;

import org.json.JSONObject;

public interface APIBehaviour {
    JSONObject getAll();
    JSONObject create();
    JSONObject getById(String id);
    JSONObject deleteById(String id);
    JSONObject updatePartialById(String id);
}
