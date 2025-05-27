package model.algorithm.text;

import app.model.algorithm.text.Kmp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KmpTest {

    @Test
    void testSimpleMatch() {
        String text = "abcdefg";
        String pattern = "cde";

        List<Integer> result = Kmp.search(pattern, text);
        assertEquals(List.of(2), result);
    }

    @Test
    void testMultipleMatches() {
        String text = "abababab";
        String pattern = "ab";

        List<Integer> result = Kmp.search(pattern, text);
        assertEquals(List.of(0, 2, 4, 6), result);
    }

    @Test
    void testNoMatch() {
        String text = "abcdefgh";
        String pattern = "xyz";

        List<Integer> result = Kmp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testPatternLongerThanText() {
        String text = "short";
        String pattern = "toolong";

        List<Integer> result = Kmp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testExactMatch() {
        String text = "pattern";
        String pattern = "pattern";

        List<Integer> result = Kmp.search(pattern, text);
        assertEquals(List.of(0), result);
    }

    @Test
    void testEmptyPattern() {
        String text = "nonempty";
        String pattern = "";

        List<Integer> result = Kmp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptyText() {
        String text = "";
        String pattern = "nonempty";

        List<Integer> result = Kmp.search(pattern, text);
        assertTrue(result.isEmpty());
    }

    @Test
    void testHeavyInputWithLateMatch() {
        StringBuilder sb = new StringBuilder("a".repeat(10000));
        sb.insert(9000, "pattern");
        String text = sb.toString();
        String pattern = "pattern";

        List<Integer> result = Kmp.search(pattern, text);
        assertEquals(List.of(9000), result);
    }

    @Test
    void testSelfOverlappingPattern() {
        String text = "aaaaaa";
        String pattern = "aaa";

        List<Integer> result = Kmp.search(pattern, text);
        assertEquals(List.of(0, 1, 2, 3), result);
    }
}
