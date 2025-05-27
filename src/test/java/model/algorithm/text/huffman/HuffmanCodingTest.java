package model.algorithm.text.huffman;

import app.model.algorithm.text.huffman.HuffmanCoding;
import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodingTest {

    private final String sampleText = "hello huffman";
    private final String filePath = "test_output.huff";

    @Test
    void compressAndDecompress_ShouldReturnOriginalText() {
        HuffmanCoding.compress(sampleText, filePath);
        String result = HuffmanCoding.decompress(filePath);
        assertEquals(sampleText, result);
    }

    @AfterEach
    void cleanup() {
        new File(filePath).delete(); // usuń plik po teście
    }
}
