package app.model.algorithm.text;

import java.util.ArrayList;
import java.util.List;

public class NaiveStringSearch {

    public static List<Integer> findAllOccurrences(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();

        // sprawdzenie przypadków brzegowych
        if (pattern == null || text == null || pattern.length() > text.length() || pattern.isEmpty()) {
            return occurrences;
        }

        int n = text.length();
        int m = pattern.length();

        // przeglądamy każdą możliwą pozycję w tekście
        for (int i = 0; i <= n - m; i++) {
            int j = 0;

            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            if (j == m) {
                occurrences.add(i);
            }
        }

        return occurrences;
    }

}