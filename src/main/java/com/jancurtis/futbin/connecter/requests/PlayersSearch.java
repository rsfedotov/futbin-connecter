package com.jancurtis.futbin.connecter.requests;

import com.jancurtis.futbin.connecter.requests.constants.PlayerSearchOrder;
import com.jancurtis.futbin.connecter.requests.constants.PlayerSearchSort;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jancurtis.futbin.connecter.requests.constants.PlayerSearchFilters.*;

public class PlayersSearch extends SearchBase {

    private String URL = BASE_URL + "/players";

    private Integer ratingFrom = 48;
    private Integer ratingTo = 99; //including
    private Integer leagueId;
    private List<String> positions = new ArrayList<>();

    private PlayerSearchSort sort;
    private PlayerSearchOrder order;

    public PlayersSearch() {
        sort = PlayerSearchSort.PS_PRICE;
        order = PlayerSearchOrder.ASC;
    }

    public PlayersSearch position(String position) {
        positions.add(position);
        return this;
    }

    public PlayersSearch league(int leagueId) {
        this.leagueId = leagueId;
        return this;
    }

    public PlayersSearch fromRating(int from) {
        ratingFrom = from;
        return this;
    }

    public PlayersSearch toRating(int to) {
        ratingTo = to;
        return this;
    }

    public PlayersSearch sort(PlayerSearchSort sort) {
        this.sort = sort;
        return this;
    }

    public PlayersSearch order(PlayerSearchOrder order) {
        this.order = order;
        return this;
    }

    public List<Map<String, String>> result() throws IOException {
        String url = buildUrl();

        //TODO logging
        System.out.println(url);

        Connection connection = Jsoup.connect(url);
        connection.maxBodySize(0); //unlimited

        Document doc = connection.get();
        Element repTb = doc.getElementById("repTb");

        Elements rowsWithHeader = repTb.select("tr");

        List<String> headersList = rowsWithHeader.get(0).
                select("th").stream()
                .map(this::getHeaderName)
                .collect(Collectors.toList());

        return IntStream.range(1, rowsWithHeader.size()).boxed()
                .map(i -> parsePlayer(rowsWithHeader.get(i), headersList))
                .collect(Collectors.toList());
    }

    private Map<String, String> parsePlayer(Element playerTr, List<String> keyList) {
        List<String> valueList = playerTr.select("td")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());

        if (keyList.size() != valueList.size()) {
            throw new IllegalStateException(
                    String.format("A number of th and td doesn't match. keys: [%s], values: [%s]",
                            keyList.toString(), valueList.toString()));
        }
        return IntStream.range(0, keyList.size()).boxed()
                .collect(Collectors.toMap(keyList::get, valueList::get));
    }

    private String buildUrl() {
        String url = String.format("%s?page=1&sort=%s&order=%s&%s=%d-%d",
                URL, sort.getFilterName(), order.getOrder(), RATING.getFilterName(), ratingFrom, ratingTo);

        if (leagueId != null) {
            url += String.format("%s=%d", LEAGUE.getFilterName(), leagueId);
        }

        if (!positions.isEmpty()) {
            url += String.format("&%s=%s", POSITION.getFilterName(), String.join(",", positions));
        }
        return url;
    }

    private String getHeaderName(Element header) {
        Elements aList = header.select("a");
        if (aList.isEmpty()) {
            return header.text();
        }
        Element a = aList.get(0);
        String dataTitle = a.attr("data-original-title");
        if (dataTitle == null) {
            return header.text();
        }
        if (!dataTitle.startsWith("Order By ")) {
            return header.text();
        }
        return dataTitle.substring("Order By ".length());
    }
}
