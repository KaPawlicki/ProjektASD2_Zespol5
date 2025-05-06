package app.model.algorithm.networkflow;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MaxFlowWithActivation {
    public static final int INF = Integer.MAX_VALUE;

    public static class FlowResult {
        private final int maxFlow;
        private final int totalActivationCost;
        private final int[][] flow;
        private final boolean[][] activated;

        public FlowResult(int maxFlow, int totalActivationCost, int[][] flow, boolean[][] activated) {
            this.maxFlow = maxFlow;
            this.totalActivationCost = totalActivationCost;
            this.flow = flow;
            this.activated = activated;
        }

        public int getMaxFlow() {
            return maxFlow;
        }

        public int getTotalCost() {
            return totalActivationCost;
        }

        public int[][] getFlow() {
            return flow;
        }

        public boolean[][] getActivated() {
            return activated;
        }
    }

    // metoda dla wielu źródeł i wielu ujść z ograniczeniami pojemności
    public static FlowResult minCostMaxFlow(int[][] capacity, int[][] activationCost,
                                            List<Integer> sources, List<Integer> sinks,
                                            Map<Integer, Integer> sourceCapacities) {
        int n = capacity.length;
        int superSource = n;
        int superSink = n + 1;

        int[][] extendedCapacity = new int[n + 2][n + 2];
        int[][] extendedActivationCost = new int[n + 2][n + 2];


        for (int i = 0; i < n + 2; i++) {
            Arrays.fill(extendedActivationCost[i], INF);
        }

        // kopiowanie oryginalnych danych
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                extendedCapacity[i][j] = capacity[i][j];
                if (capacity[i][j] > 0) {
                    extendedActivationCost[i][j] = activationCost[i][j];
                }
            }
        }

        // łączenie superźródła ze źródłami
        for (int source : sources) {
            int sourceCapacity = sourceCapacities.getOrDefault(source, INF);
            extendedCapacity[superSource][source] = sourceCapacity;
            extendedActivationCost[superSource][source] = 0; // Bezkosztowe połączenie
        }

        // łączenie ujść z superujściem
        for (int sink : sinks) {
            extendedCapacity[sink][superSink] = INF;
            extendedActivationCost[sink][superSink] = 0; // Bezkosztowe połączenie
        }

        // wywołanie algorytmu dla pojedynczego źródła i ujścia
        return minCostMaxFlow(extendedCapacity, extendedActivationCost, superSource, superSink);
    }

    // implementacja algorytmu z aktywacją krawędzi
    public static FlowResult minCostMaxFlow(int[][] capacity, int[][] activationCost,
                                            int source, int sink) {
        int n = capacity.length;
        int[][] flow = new int[n][n];
        boolean[][] activated = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(flow[i], 0);
            Arrays.fill(activated[i], false);
        }

        int maxFlow = 0;
        int totalActivationCost = 0;

        while (true) { //algorytm dijsktry do znajdowania najtanszych sciezek

            int[] distance = new int[n];
            int[] parent = new int[n];
            boolean[] visited = new boolean[n];

            Arrays.fill(distance, INF);
            Arrays.fill(parent, -1);
            Arrays.fill(visited, false);

            distance[source] = 0;

            for (int i = 0; i < n; i++) {
                // znajdowanie wierzchołka o najmniejszym koszcie
                int u = -1;
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && (u == -1 || distance[j] < distance[u])) {
                        u = j;
                    }
                }

                if (u == -1 || distance[u] == INF) break;
                visited[u] = true;

                // aktualizacja odległości do sąsiednich wierzchołków
                for (int v = 0; v < n; v++) {
                    if (capacity[u][v] > flow[u][v]) {
                        int edgeCost = activated[u][v] ? 0 : activationCost[u][v];
                        if (edgeCost == INF) continue;

                        if (distance[v] > distance[u] + edgeCost) {
                            distance[v] = distance[u] + edgeCost;
                            parent[v] = u;
                        }
                    }
                }
            }

            // przerwanie działania w przypadku nieznalezienia żadnej sciezki ze źródła do ujścia
            if (parent[sink] == -1) {
                break;
            }

            // znajdowanie maksymalnego przepływu na znalezionej ścieżce
            int pathFlow = INF;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            // obliczanie kosztu aktywacji
            int pathActivationCost = 0;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                if (!activated[u][v]) {
                    pathActivationCost += activationCost[u][v];
                    activated[u][v] = true;  // Aktywuj krawędź od razu
                }
            }

            // aktualizacja przepływu
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }

            maxFlow += pathFlow;
            totalActivationCost += pathActivationCost;
        }

        return new FlowResult(maxFlow, totalActivationCost, flow, activated);
    }
}
