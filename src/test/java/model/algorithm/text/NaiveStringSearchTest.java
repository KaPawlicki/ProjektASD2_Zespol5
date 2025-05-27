package model.algorithm.text;

import app.model.algorithm.text.NaiveStringSearch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NaiveStringSearchTest {

    @Test
    void testFindAllOccurrences_SimpleMatch() {
        String text = "hello world";
        String pattern = "world";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertEquals(List.of(6), result);
    }

    @Test
    void testFindAllOccurrences_MultipleMatches() {
        String text = "abcabcabc";
        String pattern = "abc";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertEquals(List.of(0, 3, 6), result);
    }

    @Test
    void testFindAllOccurrences_NoMatch() {
        String text = "abcdefgh";
        String pattern = "xyz";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllOccurrences_EmptyPattern() {
        String text = "text";
        String pattern = "";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllOccurrences_PatternLongerThanText() {
        String text = "short";
        String pattern = "toolongpattern";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllOccurrences_PatternEqualsText() {
        String text = "exact";
        String pattern = "exact";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertEquals(List.of(0), result);
    }

    @Test
    void testFindAllOccurrences_HeavyInput() {
        StringBuilder sb = new StringBuilder("a".repeat(10000));
        sb.insert(7000, "needle");
        String text = sb.toString();
        String pattern = "needle";

        List<Integer> result = NaiveStringSearch.search(pattern, text);
        assertEquals(List.of(7000), result);
    }
}
