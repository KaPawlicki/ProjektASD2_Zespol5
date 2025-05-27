package model.algorithm.text;

import app.model.algorithm.text.RabinKarp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RabinKarpTest {

    @Test
    void testSearch_SimpleMatch() {
        String text = "hello world";
        String pattern = "world";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertEquals(List.of(6), result);
    }

    @Test
    void testSearch_MultipleMatches() {
        String text = "abababab";
        String pattern = "ab";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertEquals(List.of(0, 2, 4, 6), result);
    }

    @Test
    void testSearch_NoMatch() {
        String text = "abcdefgh";
        String pattern = "xyz";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_EmptyPattern() {
        String text = "text";
        String pattern = "";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_PatternLongerThanText() {
        String text = "short";
        String pattern = "toolong";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_ExactMatch() {
        String text = "match";
        String pattern = "match";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertEquals(List.of(0), result);
    }

    @Test
    void testSearch_HeavyInputWithLateMatch() {
        StringBuilder sb = new StringBuilder("a".repeat(5000));
        sb.insert(4500, "pattern");
        String text = sb.toString();
        String pattern = "pattern";

        List<Integer> result = RabinKarp.search(pattern, text);
        assertEquals(List.of(4500), result);
    }
}
