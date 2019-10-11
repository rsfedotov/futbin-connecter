package com.jancurtis.futbin.connecter.requests.constants;

public enum PlayerSearchFilters {

    RATING("player_rating"),
    PS_PRICE("ps_price"),
    NAME("search"),
    LEAGUE("league"),
    POSITION("position");

    private String urlFilter;

    PlayerSearchFilters(String urlFilter) {
        this.urlFilter = urlFilter;
    }

    public String getFilterName() {
        return urlFilter;
    }
}
