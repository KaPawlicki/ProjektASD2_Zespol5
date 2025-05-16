package app.model.algorithm.text.huffman;

import java.util.*;

public class FrequencyAnalyzer {
    // obliczanie częstotliwości występowania znaków w tekście
    public static Map<Character, Integer> calculateFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }
}
