package com.matchingSystem.API.ClientInterfaces;

public interface BaseAPIInterface {
    Object getAll(String queryParam);
    Object create(StringBuilder params);
    Object getById(String id, String queryParam);
    boolean deleteById(String id);
    Object updatePartialById(String id, StringBuilder params);
}
