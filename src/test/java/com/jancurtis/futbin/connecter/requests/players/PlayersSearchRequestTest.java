package com.jancurtis.futbin.connecter.requests.players;

import com.jancurtis.futbin.connecter.requests.PlayersSearch;
import com.jancurtis.futbin.connecter.requests.constants.PlayerAttributes;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlayersSearchRequestTest {

    private static final int RATING_FROM = 87;
    private static final int RATING_TO = 50;
    private static final String POSITION_FILTER = "CB,ST";
    private static final int LEAGUE_ID = 31;
    private static final String DEFEND_POSITIONS = "CB,LB,RB,LWB,RWB";

    @Test
    public void testNoFilters() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search.result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
    }

    @Test
    public void testFromRatingFilter() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search.fromRating(RATING_FROM).result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());

        results.forEach(this::assertRatingFrom);
    }

    @Test
    public void testToRatingFilter() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search.toRating(RATING_TO).result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());

        results.forEach(this::assertRatingTo);
    }

    @Test
    public void testPositionFilter() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search.position(POSITION_FILTER).result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());

        results.forEach(player -> assertPosition(player, POSITION_FILTER));
    }

    @Test
    public void testPositionFilterAggregation() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search.position("Defenders").result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());

        results.forEach(player -> assertPosition(player, DEFEND_POSITIONS));
    }

    @Test
    public void testLeagueFilter() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search.league(LEAGUE_ID).result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());
    }

    private void assertPosition(Map<String, String> player, String expectedPositions) {
        String actualPosition = player.get(PlayerAttributes.POSITION.getParamName());
        Assert.assertTrue(String.format("Unexpected position '%s' of the player '%s', expected positions '%s'",
                actualPosition, player.get(PlayerAttributes.NAME.getParamName()), expectedPositions),
                Arrays.asList(expectedPositions.split(",")).contains(actualPosition));
    }

    private void assertRatingFrom(Map<String, String> player) {
        String ratingStr = player.get(PlayerAttributes.RATING.getParamName());
        Integer actualRating = Integer.valueOf(ratingStr);
        Assert.assertTrue(RATING_FROM <= actualRating);
    }

    private void assertRatingTo(Map<String, String> player) {
        String ratingStr = player.get(PlayerAttributes.RATING.getParamName());
        Integer actualRating = Integer.valueOf(ratingStr);
        Assert.assertTrue(RATING_TO >= actualRating);
    }
}
