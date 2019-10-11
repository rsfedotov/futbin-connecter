package com.jancurtis.futbin.connecter.requests.constants;

public enum PlayerAttributes {

    RATING("Rating"),
    PRICE("Price"),
    NAME("Name"),
    POSITION("Position");

    private String paramName;

    PlayerAttributes(String paramName) {
        this.paramName = paramName;
    }


    public String getParamName() {
        return paramName;
    }
}
