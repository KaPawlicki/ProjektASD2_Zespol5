package model.algorithm.text.huffman;

import app.model.algorithm.text.huffman.FrequencyAnalyzer;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class FrequencyAnalyzerTest {

    @Test
    void calculateFrequency_ShouldReturnCorrectMap() {
        String input = "aabbc";
        Map<Character, Integer> freq = FrequencyAnalyzer.calculateFrequency(input);

        assertEquals(2, freq.get('a'));
        assertEquals(2, freq.get('b'));
        assertEquals(1, freq.get('c'));
    }
}
