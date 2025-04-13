package app.model;

import java.util.*;

public class EdmondsKarp {
    public static int maxFlow(int[][] capacity, List<Integer> sources, List<Integer> sinks) { //wersja dla wielu ujsc i zrodel
        int n = capacity.length;

        int superSource = n;
        int superSink = n + 1;

        int[][] extendedCapacity = new int[n + 2][n + 2]; //tworzenie powiekszonej sieci

        for (int i = 0; i < n; i++) {
            System.arraycopy(capacity[i], 0, extendedCapacity[i], 0, n);
        }

        for (int i = 0; i < sources.size(); i++) { //laczenie superzrodla z innymi zrodlami krawedziami o nieskonczonej przepustowosci
            int source = sources.get(i);
            extendedCapacity[superSource][source] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < sinks.size(); i++) { //to samo ale dla ujscia
            int sink = sinks.get(i);
            extendedCapacity[sink][superSink] = Integer.MAX_VALUE;
        }

        return maxFlow(extendedCapacity, superSource, superSink); //wywolanie klasycznej wersji algorytmu dla nowych danych
    }


    public static int maxFlow(int[][] capacity, int source, int sink) { //algorytm edmondsa karpa
        int n = capacity.length;
        int[][] flow = new int[n][n];
        int maxFlow = 0;

        while (true) { //wyszukiwanie sciezek powiekszajacych algorytmem bfs
            int[] parent = new int[n];
            Arrays.fill(parent, -1);

            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);
            parent[source] = -2;

            while (!queue.isEmpty() && parent[sink] == -1) {
                int u = queue.poll();

                for (int v = 0; v < n; v++) {
                    if (parent[v] == -1 && capacity[u][v] > flow[u][v]) {
                        parent[v] = u;
                        queue.add(v);
                    }
                }
            }

            if (parent[sink] == -1) {
                break;
            }

            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}
