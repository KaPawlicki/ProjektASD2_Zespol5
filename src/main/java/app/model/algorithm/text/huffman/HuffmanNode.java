package app.model.algorithm.text.huffman;

import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
    private static final long serialVersionUID = 1L;

    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.character = '\0';
        this.left = left;
        this.right = right;
    }

    // porównywanie węzłów na podstawie częstotliwości (dla PriorityQueue)
    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}
