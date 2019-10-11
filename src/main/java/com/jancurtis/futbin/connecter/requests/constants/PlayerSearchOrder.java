package com.jancurtis.futbin.connecter.requests.constants;

public enum PlayerSearchOrder {

    ASC("asc"),
    DESC("desc");

    private String order;

    PlayerSearchOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
