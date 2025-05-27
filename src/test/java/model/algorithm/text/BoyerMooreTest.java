package model.algorithm.text;

import app.model.algorithm.text.BoyerMoore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoyerMooreTest {

    @Test
    void testSearch_SimpleMatch() {
        String text = "abcxabcdabxabcdabcdabcy";
        String pattern = "abcdabcy";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertEquals(List.of(15), result);
    }

    @Test
    void testSearch_MultipleMatches() {
        String text = "ababababca";
        String pattern = "ab";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertEquals(List.of(0, 2, 4, 6), result);
    }

    @Test
    void testSearch_NoMatch() {
        String text = "abcdefgh";
        String pattern = "xyz";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_EmptyPattern() {
        String text = "some text here";
        String pattern = "";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_PatternLongerThanText() {
        String text = "short";
        String pattern = "longerpattern";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_PatternEqualsText() {
        String text = "exactmatch";
        String pattern = "exactmatch";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertEquals(List.of(0), result);
    }

    @Test
    void testSearch_HeavyInput() {
        StringBuilder sb = new StringBuilder("a".repeat(10000));
        sb.insert(5000, "needle");
        String text = sb.toString();
        String pattern = "needle";

        List<Integer> result = BoyerMoore.search(pattern, text);
        assertEquals(List.of(5000), result);
    }
}
