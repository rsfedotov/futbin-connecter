package com.jancurtis.futbin.connecter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

public class JSoupShowcase {

    private static final String SEARCH_URL =
            "https://www.futbin.com/players?page=1&player_rating=82-85&sort=ps_price&league=31&order=asc";

    @Test
    public void testSearchPlayers() throws Exception {
        Document doc = Jsoup.connect(SEARCH_URL).get();
        Element repTb = doc.getElementById("repTb");
        Assert.assertNotNull(repTb);
        Elements players = repTb.select("tr");
        Assert.assertNotNull(players);
    }

    @Test
    public void testExtractHeaders() throws Exception {
        Document doc = Jsoup.connect(SEARCH_URL).get();
        Element repTb = doc.getElementById("repTb");
        Assert.assertNotNull(repTb);
        repTb.select("th").forEach(h -> System.out.println(h.text()));
    }

}
