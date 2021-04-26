package com.matchingSystem;

public class Constant {
    public static final int IS_STUDENT = 0;
    public static final int IS_TUTOR = 1;

    public static final int NONE = 0;
    public static final int COMPETENCIES = 1;
    public static final int COMPETENCIES_SUBJECT = 2;
    public static final int BIDS = 3;
    public static final int QUALIFICATIONS = 4;

    // field type string
    public static final String NONE_S = "?fields=";
    public static final String COMPETENCIES_S = "?fields=competencies";
    public static final String COMPETENCIES_SUBJECT_S = "?fields=competencies.subject";
    public static final String QUALIFICATIONS_S = "?fields=qualifications";
    public static final String INITIATEDBIDS_S = "?fields=initiatedBids";

    // bid type string
    public static final int OPENBID = 0;
    public static final int CLOSEBID = 1;
    public static final String OPENBID_S = "open";
    public static final String CLOSEBID_S = "close";

}
