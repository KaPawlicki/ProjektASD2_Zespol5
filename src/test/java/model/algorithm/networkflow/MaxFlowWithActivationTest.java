package model.algorithm.networkflow;

import app.model.algorithm.networkflow.MaxFlowWithActivation;
import app.model.algorithm.networkflow.MaxFlowWithActivation.FlowResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaxFlowWithActivationTest {

    @Test
    public void testSimpleGraph() {
        // Graf 4 wierzchołków (0 źródło, 3 ujście)
        int[][] capacity = {
                {0, 3, 2, 0},
                {0, 0, 0, 2},
                {0, 1, 0, 3},
                {0, 0, 0, 0}
        };

        // Koszt aktywacji krawędzi, INF oznacza krawędź niedostępną
        int INF = MaxFlowWithActivation.INF;
        int[][] activationCost = {
                {INF, 1, 2, INF},
                {INF, INF, INF, 3},
                {INF, 1, INF, 1},
                {INF, INF, INF, INF}
        };

        int source = 0;
        int sink = 3;

        FlowResult result = MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, source, sink);

        // Maksymalny przepływ
        assertEquals(4, result.getMaxFlow());

        // Sprawdzenie, że koszt aktywacji jest sumą kosztów aktywowanych krawędzi
        // Możliwe minimalne koszty to: krawędź 0->1 (1), 1->3 (3), 0->2 (2), 2->3 (1)
        // Zatem 1+3+2+1 = 7
        assertEquals(7, result.getTotalCost());

        // Sprawdzenie aktywacji krawędzi
        boolean[][] activated = result.getActivated();
        assertTrue(activated[0][1]);
        assertTrue(activated[1][3]);
        assertTrue(activated[0][2]);
        assertTrue(activated[2][3]);
    }

    @Test
    public void testNoPath() {
        int[][] capacity = {
                {0, 0},
                {0, 0}
        };
        int INF = MaxFlowWithActivation.INF;
        int[][] activationCost = {
                {INF, INF},
                {INF, INF}
        };

        FlowResult result = MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, 0, 1);

        assertEquals(0, result.getMaxFlow());
        assertEquals(0, result.getTotalCost());
    }

    @Test
    public void testSingleEdge() {
        int[][] capacity = {
                {0, 10},
                {0, 0}
        };
        int INF = MaxFlowWithActivation.INF;
        int[][] activationCost = {
                {INF, 5},
                {INF, INF}
        };

        FlowResult result = MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, 0, 1);

        assertEquals(10, result.getMaxFlow());
        assertEquals(5, result.getTotalCost());
        assertTrue(result.getActivated()[0][1]);
    }

    @Test
    public void testEdgeAlreadyActivated() {
        // Sprawdzenie, czy koszt aktywacji nie nalicza się ponownie dla tych samych krawędzi
        int[][] capacity = {
                {0, 5, 5},
                {0, 0, 10},
                {0, 0, 0}
        };
        int INF = MaxFlowWithActivation.INF;
        int[][] activationCost = {
                {INF, 1, 2},
                {INF, INF, 1},
                {INF, INF, INF}
        };

        FlowResult firstResult = MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, 0, 2);

        assertEquals(10, firstResult.getMaxFlow());
        assertEquals(4, firstResult.getTotalCost());  // 1 + 1 + 2

        // Uruchamiamy ponownie na podstawie stanu po pierwszym przebiegu (symulacja)
        // W praktyce bez utrzymania stanu nie zadziała, ale test pokazuje oczekiwania

        // Załóżmy, że aktywacja już jest, więc koszt na krawędziach to 0
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity.length; j++) {
                if (firstResult.getActivated()[i][j]) {
                    activationCost[i][j] = 0;
                }
            }
        }

        FlowResult secondResult = MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, 0, 2);

        assertEquals(10, secondResult.getMaxFlow());
        assertEquals(0, secondResult.getTotalCost());
    }
}
