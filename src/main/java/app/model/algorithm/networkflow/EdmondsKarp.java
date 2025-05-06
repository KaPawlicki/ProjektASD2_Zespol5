package app.model.algorithm.networkflow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarp {
    public static int maxFlow(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] flow = new int[n][n];
        int maxFlow = 0;

        while (true) {
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
