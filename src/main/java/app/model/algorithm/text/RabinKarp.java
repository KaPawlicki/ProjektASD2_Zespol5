package app.model.algorithm.text;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    private static final int PRIME = 101; // Liczba pierwsza używana jako modulo
    private static final int BASE = 10;  // Podstawa

    public static List<Integer> search(String pattern, String text) {
        List<Integer> positions = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();

        // Jeśli wzorzec jest dłuższy niż tekst - brak dopasowań
        if (m > n) return positions;

        // Obliczanie wartości haszu dla wzorca
        long patternHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % PRIME;
        }

        // Obliczanie początkowej wartości haszu dla pierwszego podciągu tekstu
        long textHash = 0;
        for (int i = 0; i < m; i++) {
            textHash = (BASE * textHash + text.charAt(i)) % PRIME;
        }

        long h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % PRIME;
        }

        // Przesuwanie okna i sprawdzanie dopasowań
        for (int i = 0; i <= n - m; i++) {
            // Sprawdzanie dokladnego dopasowania - naiwne
            if (patternHash == textHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    positions.add(i);
                }
            }

            // Obliczanie wartosci haszu dla następnego podciągu tekstu
            if (i < n - m) {
                // Usuń wartość najstarszego znaku
                textHash = (textHash - h * text.charAt(i)) % PRIME;
                if (textHash < 0) textHash += PRIME; // Zapewnienie dodatniej wartości

                // Dodaj wartość nowego znaku
                textHash = (textHash * BASE + text.charAt(i + m)) % PRIME;
            }
        }

        return positions;
    }
}
