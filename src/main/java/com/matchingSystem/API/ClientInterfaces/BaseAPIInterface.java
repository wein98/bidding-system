package com.matchingSystem.API.ClientInterfaces;

public interface BaseAPIInterface {
    Object getAll();
    Object create(StringBuilder params);
    Object getById(String id);
    boolean deleteById(String id);
    Object updatePartialById(String id, StringBuilder params);
}
