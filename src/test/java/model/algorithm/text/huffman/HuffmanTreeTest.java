package model.algorithm.text.huffman;

import app.model.algorithm.text.huffman.FrequencyAnalyzer;
import app.model.algorithm.text.huffman.HuffmanTree;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTreeTest {

    @Test
    void encodeAndDecode_ShouldReturnOriginalText() {
        String input = "abababccccc";

        Map<Character, Integer> freq = FrequencyAnalyzer.calculateFrequency(input);
        HuffmanTree tree = new HuffmanTree();
        tree.buildTree(freq);

        String encoded = tree.encode(input);
        String decoded = tree.decode(encoded);

        assertEquals(input, decoded);
    }

    @Test
    void rebuildTree_ShouldReconstructCorrectly() {
        String input = "abcabc";

        HuffmanTree tree = new HuffmanTree();
        tree.buildTree(FrequencyAnalyzer.calculateFrequency(input));

        Map<Character, String> originalCodes = tree.getHuffmanCodes();
        String encoded = tree.encode(input);

        HuffmanTree newTree = new HuffmanTree();
        newTree.rebuildTree(originalCodes);
        String decoded = newTree.decode(encoded);

        assertEquals(input, decoded);
    }
}
