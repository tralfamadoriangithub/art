package com.jttss.botendpoint.youtube;

public enum SearchContentType {

    CHANNEL("channel"),
    PLAYLIST("playlist"),
    VIDEO("video");

    private String stringValue;

    private SearchContentType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String stringValue() {
        return stringValue;
    }
}
