package app.model.algorithm.text.huffman;

import java.util.Map;

public class HuffmanCompressedData {
    private Map<Character, String> huffmanCodes;
    private String encodedText;

    public HuffmanCompressedData(Map<Character, String> huffmanCodes, String encodedText) {
        this.huffmanCodes = huffmanCodes;
        this.encodedText = encodedText;
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    public String getEncodedText() {
        return encodedText;
    }
}
