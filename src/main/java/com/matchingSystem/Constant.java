package com.matchingSystem;

public class Constant {
    public static final int IS_STUDENT = 0;
    public static final int IS_TUTOR = 1;

    // field type string
    public static final String NONE = "?fields=";
    public static final String COMPETENCIES = "?fields=competencies";
    public static final String COMPETENCIES_SUBJECT = "?fields=competencies.subject";
    public static final String QUALIFICATIONS = "?fields=qualifications";
    public static final String INITIATEDBIDS = "?fields=initiatedBids";
    public static final String BID_MESSAGES = "?fields=messages";

    // bid type string
    public static final int OPENBID = 0;
    public static final int CLOSEBID = 1;
    public static final String OPENBID_S = "open";
    public static final String CLOSEBID_S = "close";

    // biddings view type string
    public static final int OPEN_BIDDING_VIEW = 10;
    public static final int CLOSE_BIDDING_VIEW = 11;
    public static final int OFFER_BIDS_VIEW = 12;
    public static final int TUTOR_OPEN_BIDDING_VIEW = 13;

}
