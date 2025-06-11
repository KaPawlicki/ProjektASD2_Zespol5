package app.model.algorithm.text;

import java.util.ArrayList;
import java.util.List;

public class Kmp {
    public static List<Integer> search(String pattern, String text) {
        List<Integer> positions = new ArrayList<>();
        if(pattern.isEmpty() || text.isEmpty() || pattern.length() > text.length()) return positions;

        int m = pattern.length();
        int n = text.length();

        // tworzymy tablicę lps (longest prefix suffix) dla wzorca
        int[] lps = new int[m];
        computeLPSArray(pattern, m, lps);

        int i = 0; // indeks dla text[]
        int j = 0; // indeks dla pattern[]
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == m) {
                positions.add(i-j);
                j = lps[j - 1];
            }
            // niezgodność po dopasowaniu j znaków
            else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return positions;
    }

    // funkcja do obliczania tablicy lps dla wzorca
    private static void computeLPSArray(String pattern, int M, int[] lps) {
        // długość najdłuższego prefiksu sufiksu z poprzedniego kroku
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] jest zawsze 0

        // obliczamy lps[i] dla i = 1 do M-1
        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}
