package com.matchingSystem.API.ClientInterfaces;

public interface QualificationAPIInterface extends BaseAPIInterface {
    StringBuilder parseToJson(String title, String desc, String verified, String ownerId);
}
