package model.algorithm.networkflow;

import app.model.algorithm.networkflow.EdmondsKarp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdmondsKarpTest {

    @Test
    public void testMaxFlowSimpleCase() {
        // Graf z 6 wierzchołkami (0 to źródło, 5 to ujście)
        int[][] capacity = {
                {0, 16, 13, 0, 0, 0},
                {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0},
                {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4},
                {0, 0, 0, 0, 0, 0}
        };

        int source = 0;
        int sink = 5;

        int result = EdmondsKarp.maxFlow(capacity, source, sink);

        // Spodziewany maksymalny przepływ to 23 (klasyczny przykład)
        assertEquals(23, result);
    }

    @Test
    public void testNoPath() {
        int[][] capacity = {
                {0, 0},
                {0, 0}
        };
        assertEquals(0, EdmondsKarp.maxFlow(capacity, 0, 1));
    }

    @Test
    public void testZeroCapacity() {
        int[][] capacity = {
                {0, 5},
                {0, 0}
        };
        // Źródło i ujście są połączone, ale przepustowość to tylko 5
        assertEquals(5, EdmondsKarp.maxFlow(capacity, 0, 1));
    }

    @Test
    public void testDisconnectedGraph() {
        int[][] capacity = {
                {0, 10, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 10},
                {0, 0, 0, 0}
        };
        assertEquals(0, EdmondsKarp.maxFlow(capacity, 0, 3));
    }

    @Test
    public void testMultiplePathsSameCapacity() {
        int[][] capacity = {
                {0, 5, 5, 0},
                {0, 0, 0, 5},
                {0, 0, 0, 5},
                {0, 0, 0, 0}
        };
        // Dwie ścieżki o przepustowości 5 każda
        assertEquals(10, EdmondsKarp.maxFlow(capacity, 0, 3));
    }

    @Test
    public void testLoopEdgeIgnored() {
        int[][] capacity = {
                {10, 5},
                {0, 0}
        };
        // Pętla na 0 jest ignorowana
        capacity[0][0] = 100; // nie powinna wpływać na wynik
        assertEquals(5, EdmondsKarp.maxFlow(capacity, 0, 1));
    }

    @Test
    public void testBackEdgeScenario() {
        int[][] capacity = {
                {0, 10, 0},
                {0, 0, 5},
                {7, 0, 0}
        };
        // Flow może wrócić przez wierzchołek 2 do 0
        assertEquals(5, EdmondsKarp.maxFlow(capacity, 0, 2));
    }


}
