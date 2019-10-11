package com.jancurtis.futbin.connecter.requests.constants;

public enum PlayerSearchSort {

    RATING("player_rating"),
    PS_PRICE("ps_price");

    private String urlFilter;

    PlayerSearchSort(String urlFilter) {
        this.urlFilter = urlFilter;
    }

    public String getFilterName() {
        return urlFilter;
    }
}
