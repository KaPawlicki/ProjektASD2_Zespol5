package app.model.algorithm.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoyerMoore {

    public static List<Integer> findAllOccurrences(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();

        if (pattern == null || text == null || pattern.length() > text.length() || pattern.isEmpty()) {
            return occurrences;
        }

        int m = pattern.length();
        int n = text.length();

        // wstępne przetwarzanie wzorca
        Map<Character, Integer> badCharShift = preprocessBadCharacterRule(pattern);
        int[] goodSuffixShift = preprocessGoodSuffixRule(pattern);

        // główna pętla wyszukiwania
        int shift = 0;
        while (shift <= (n - m)) {
            int j = m - 1;

            // dopasowywanie znaków wzorca z tekstem od prawej do lewej
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                occurrences.add(shift);

                // przesunięcie wzorca, aby kontynuować poszukiwanie
                if (shift + m < n) {
                    char nextChar = text.charAt(shift + m);
                    shift += m - badCharShift.getOrDefault(nextChar, -1);
                } else {
                    shift += 1;
                }
            } else {
                // obliczanie przesunięcia wzorca na podstawie reguł bad character i good suffix
                char badChar = text.charAt(shift + j);
                int badCharSkip = j - badCharShift.getOrDefault(badChar, -1);
                int goodSuffixSkip = goodSuffixShift[j];

                shift += Math.max(badCharSkip, goodSuffixSkip);
            }
        }

        return occurrences;
    }


    private static Map<Character, Integer> preprocessBadCharacterRule(String pattern) {
        Map<Character, Integer> badCharShift = new HashMap<>();
        int m = pattern.length();

        for (int i = 0; i < m; i++) {
            badCharShift.put(pattern.charAt(i), i);
        }

        return badCharShift;
    }


    private static int[] preprocessGoodSuffixRule(String pattern) {
        int m = pattern.length();
        int[] goodSuffixShift = new int[m];
        int[] suffixLength = computeSuffixLength(pattern);

        // inicjalizacja, domyślnie przesuwamy o długość wzorca
        Arrays.fill(goodSuffixShift, m);

        // przypadek 1: dla każdego podsłowa, które jest sufiksem i ma dopasowanie w wzorcu
        int j = 0;
        for (int i = m - 1; i >= 0; i--) {
            if (suffixLength[i] == i + 1) {
                // znaleziono sufiks, który pojawia się na początku wzorca
                for (; j < m - 1 - i; j++) {
                    if (goodSuffixShift[j] == m) {
                        goodSuffixShift[j] = m - 1 - i;
                    }
                }
            }
        }

        // przypadek 2: dla każdego suffiksu, który ma dopasowanie w innym miejscu wzorca
        for (int i = 0; i <= m - 2; i++) {
            goodSuffixShift[m - 1 - suffixLength[i]] = m - 1 - i;
        }

        return goodSuffixShift;
    }


    private static int[] computeSuffixLength(String pattern) {
        int m = pattern.length();
        int[] suffix = new int[m];
        suffix[m - 1] = m;

        int g = m - 1;
        int f = 0;

        for (int i = m - 2; i >= 0; i--) {
            if (i > g && suffix[i + m - 1 - f] < i - g) {
                suffix[i] = suffix[i + m - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;

                while (g >= 0 && pattern.charAt(g) == pattern.charAt(g + m - 1 - f)) {
                    g--;
                }

                suffix[i] = f - g;
            }
        }

        return suffix;
    }
}