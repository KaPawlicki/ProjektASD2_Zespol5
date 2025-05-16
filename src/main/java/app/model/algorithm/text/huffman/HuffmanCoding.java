package app.model.algorithm.text.huffman;

import java.io.*;
import java.util.*;

public class HuffmanCoding {
    public static void compress(String text, String outputFilePath) throws IOException {

        Map<Character, Integer> frequencyMap = FrequencyAnalyzer.calculateFrequency(text);

        // budowanie drzewa Huffmana
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildTree(frequencyMap);

        //kodowanie tekstu
        String encodedText = huffmanTree.encode(text);

        FileHandler.saveCompressedFile(huffmanTree.getHuffmanCodes(), encodedText, outputFilePath);

        System.out.println("Plik został skompresowany i zapisany jako: " + outputFilePath);
        System.out.println("Oryginalny rozmiar (w znakach): " + text.length());
        System.out.println("Skompresowany rozmiar (w bitach): " + encodedText.length());
    }

    public static String decompress(String inputFilePath) throws IOException, ClassNotFoundException {
        // wczytywanie skompresowanego plikuk
        HuffmanCompressedData compressedData = FileHandler.loadCompressedFile(inputFilePath);

        // odbudowywanie drzewa Huffmana
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.rebuildTree(compressedData.getHuffmanCodes());

        // dekodowanie tekstu
        String decodedText = huffmanTree.decode(compressedData.getEncodedText());
        return decodedText;
    }


}