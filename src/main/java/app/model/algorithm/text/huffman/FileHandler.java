package app.model.algorithm.text.huffman;

import java.io.*;
import java.util.*;

public class FileHandler {

    // zapisywanie skompresowanych danych do pliku
    public static void saveCompressedFile(Map<Character, String> huffmanCodes, String encodedText, String outputFilePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFilePath))) {
            // Zapisz mapę kodów Huffmana
            oos.writeObject(huffmanCodes);

            // Zapisz zakodowany tekst jako bitset
            int lastBitLength = encodedText.length() % 8;
            oos.writeInt(lastBitLength > 0 ? lastBitLength : 8);

            // Konwersja ciągu bitów na bajty
            byte[] bytes = new byte[(encodedText.length() + 7) / 8];
            for (int i = 0; i < encodedText.length(); i++) {
                if (encodedText.charAt(i) == '1') {
                    bytes[i / 8] |= (1 << (7 - (i % 8)));
                }
            }

            oos.writeInt(bytes.length);
            oos.write(bytes);
        }
    }

    // wczytywanie skompresowanych danych z pliku
    @SuppressWarnings("unchecked")
    public static HuffmanCompressedData loadCompressedFile(String inputFilePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFilePath))) {
            // wczytywanie mapy kodów Huffmana
            Map<Character, String> huffmanCodes = (Map<Character, String>) ois.readObject();

            // wczytywanie liczby bitów w ostatnim bajcie
            int lastBitLength = ois.readInt();

            // wczytywanie zakodowanych bitów
            int bytesLength = ois.readInt();
            byte[] bytes = new byte[bytesLength];
            ois.readFully(bytes);

            // konwersja bajtów na ciąg bitów
            StringBuilder encodedText = new StringBuilder();
            for (int i = 0; i < bytesLength; i++) {
                for (int j = 0; j < (i < bytesLength - 1 ? 8 : lastBitLength); j++) {
                    encodedText.append((bytes[i] & (1 << (7 - j))) != 0 ? '1' : '0');
                }
            }

            return new HuffmanCompressedData(huffmanCodes, encodedText.toString());
        }
    }
}
