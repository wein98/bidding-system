package com.matchingSystem.API.ClientInterfaces;

import com.matchingSystem.API.ClientInterfaces.BaseAPIInterface;

public interface CompetencyAPIInterface extends BaseAPIInterface {
    StringBuilder parseToJsonForCreate(String ownerId, String subjectId, int level);
    StringBuilder parseToJsonForPartialUpdate(int level);
}
