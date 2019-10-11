package com.jancurtis.futbin.connecter.requests.players;

import com.jancurtis.futbin.connecter.requests.PlayersSearch;
import com.jancurtis.futbin.connecter.requests.constants.PlayerAttributes;
import com.jancurtis.futbin.connecter.requests.constants.PlayerSearchOrder;
import com.jancurtis.futbin.connecter.requests.constants.PlayerSearchSort;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PlayersSearchRequestSortOrderTest {

    @Test
    public void testRatingOrderAsc() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search
                .fromRating(95).toRating(99)
                .sort(PlayerSearchSort.RATING).order(PlayerSearchOrder.ASC)
                .result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());

        Comparator<Map<String, String>> ratingComparator = Comparator.comparing(
                (Map<String, String> o) -> Integer.valueOf(o.get(PlayerAttributes.RATING.getParamName())));

        Assert.assertTrue("Resulting list sorted improperly", isSortedAsc(results, ratingComparator));
    }

    @Test
    public void testRatingOrderDesc() throws IOException {
        PlayersSearch search = new PlayersSearch();
        List<Map<String, String>> results = search
                .fromRating(95).toRating(99)
                .sort(PlayerSearchSort.RATING).order(PlayerSearchOrder.DESC)
                .result();

        Assert.assertNotNull(results);
        Assert.assertFalse(results.isEmpty());

        Comparator<Map<String, String>> ratingComparator = Comparator.comparing(
                (Map<String, String> o) -> Integer.valueOf(o.get(PlayerAttributes.RATING.getParamName())));

        Assert.assertTrue("Resulting list sorted improperly", isSortedDesc(results, ratingComparator));
    }

    private static <T> boolean isSortedAsc(List<T> list, Comparator<T> comp) {
        for (int i = 0; i < list.size() - 1; ++i) {
            T left = list.get(i);
            T right = list.get(i + 1);
            if (comp.compare(left, right) > 0) {
                return false;
            }
        }
        return true;
    }

    private static <T> boolean isSortedDesc(List<T> list, Comparator<T> comp) {
        for (int i = 0; i < list.size() - 1; ++i) {
            T left = list.get(i);
            T right = list.get(i + 1);
            if (comp.compare(left, right) < 0) {
                return false;
            }
        }
        return true;
    }
}
