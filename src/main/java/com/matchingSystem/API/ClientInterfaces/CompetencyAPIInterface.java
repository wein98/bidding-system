package com.matchingSystem.API.ClientInterfaces;

public interface CompetencyAPIInterface extends BaseAPIInterface {
    StringBuilder parseToJsonForCreate(String ownerId, String subjectId, int level);
    StringBuilder parseToJsonForPartialUpdate(int level);
}
