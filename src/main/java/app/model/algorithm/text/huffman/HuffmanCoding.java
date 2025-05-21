package app.model.algorithm.text.huffman;

import java.io.*;
import java.util.*;

public class HuffmanCoding {
    public static void compress(String text, String outputFilePath) {

        Map<Character, Integer> frequencyMap = FrequencyAnalyzer.calculateFrequency(text);

        // budowanie drzewa Huffmana
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildTree(frequencyMap);

        //kodowanie tekstu
        String encodedText = huffmanTree.encode(text);

        try {
            FileHandler.saveCompressedFile(huffmanTree.getHuffmanCodes(), encodedText, outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println("Plik został skompresowany i zapisany jako: " + outputFilePath);
//        System.out.println("Oryginalny rozmiar (w znakach): " + text.length());
//        System.out.println("Skompresowany rozmiar (w bitach): " + encodedText.length());
    }

    public static String decompress(String inputFilePath) {
        // wczytywanie skompresowanego plikuk
        HuffmanCompressedData compressedData = null;
        try {
            compressedData = FileHandler.loadCompressedFile(inputFilePath);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // odbudowywanie drzewa Huffmana
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.rebuildTree(compressedData.getHuffmanCodes());

        // dekodowanie tekstu
        return huffmanTree.decode(compressedData.getEncodedText());
    }


}