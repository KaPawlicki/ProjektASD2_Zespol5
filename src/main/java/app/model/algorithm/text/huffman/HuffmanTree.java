package app.model.algorithm.text.huffman;

import java.util.*;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Character, String> huffmanCodes;

    public HuffmanTree() {
        this.huffmanCodes = new HashMap<>();
    }

    // budowanie drzewa Huffmana na podstawie częstotliwości znaków
    public void buildTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();


        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }


        while (priorityQueue.size() > 1) {

            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
            priorityQueue.add(parent);
        }

        root = priorityQueue.poll();

        generateCodes();
    }

    // rekurencyjne budowanie kodów Huffmana
    private void buildHuffmanCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }


        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.character, code);
        }

        buildHuffmanCodes(node.left, code + "0", huffmanCodes);
        buildHuffmanCodes(node.right, code + "1", huffmanCodes);
    }

    // generowanie kodów Huffmana
    public void generateCodes() {
        huffmanCodes.clear();
        buildHuffmanCodes(root, "", huffmanCodes);
    }

    // kodowanie tekstu za pomocą kodów Huffmana
    public String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char character : text.toCharArray()) {
            encodedText.append(huffmanCodes.get(character));
        }
        return encodedText.toString();
    }

    // dekodowanie zakodowanego tekstu
    public String decode(String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : encodedText.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            // Jeśli osiągnęliśmy liść, dodaj jego znak do wyniku i wróć do korzenia
            if (current.left == null && current.right == null) {
                decodedText.append(current.character);
                current = root;
            }
        }

        return decodedText.toString();
    }

    // odbudowywanie drzewa Huffmana na podstawie kodów
    public void rebuildTree(Map<Character, String> codes) {
        root = new HuffmanNode('\0', 0);

        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            char character = entry.getKey();
            String code = entry.getValue();

            HuffmanNode current = root;
            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == '0') {
                    if (current.left == null) {
                        current.left = new HuffmanNode('\0', 0);
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = new HuffmanNode('\0', 0);
                    }
                    current = current.right;
                }

                if (i == code.length() - 1) {
                    current.character = character;
                }
            }
        }


        this.huffmanCodes = new HashMap<>(codes);
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    public HuffmanNode getRoot() {
        return root;
    }
}
