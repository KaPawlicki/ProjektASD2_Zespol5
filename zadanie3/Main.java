import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj liczbę wierzchołków i krawędzi: ");
        int vertices = scanner.nextInt();
        int edges = scanner.nextInt();

        int[][] graph = new int[vertices][vertices];

        System.out.println("Podaj krawędzie w formacie: v u waga");
        for (int i = 0; i < edges; i++) {
            int v = scanner.nextInt();
            int u = scanner.nextInt();
            int weight = scanner.nextInt();
            graph[v][u] = weight;
        }

        System.out.print("Podaj źródło: ");
        int source = scanner.nextInt();
        System.out.print("Podaj ujście: ");
        int sink = scanner.nextInt();
        System.out.println("Ford-Fulkerson: " + maxFlow(graph, source, sink, graph.length));
        System.out.println("Edmond-karp: " + maxFlow(graph, source, sink, graph.length));
    }
    // Implementacja DFS do znajdowania ścieżki powiększającej
    public static boolean dfs(int[][] residualGraph, int source, int sink, int[] parent, int V) {
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);

        Stack<Integer> stack = new Stack<>();
        stack.push(source);
        visited[source] = true;
        parent[source] = -1;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int v = 0; v < V; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    stack.push(v);
                    parent[v] = u;
                    visited[v] = true;
                    if (v == sink) return true;
                }
            }
        }
        return false;
    }

    public static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent, int V) {
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < V; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                    if (v == sink) return true;
                }
            }
        }
        return false;
    }

    // Implementacja algorytmu Forda-Fulkersona
    public static int maxFlow(int[][] graph, int source, int sink, int V) {
        int[][] residualGraph = new int[V][V];
        for (int i = 0; i < V; i++) {
            System.arraycopy(graph[i], 0, residualGraph[i], 0, V);
        }

        int[] parent = new int[V];
        int maxFlow = 0;

        while (dfs(residualGraph, source, sink, parent,V)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    // Implementacja algorytmu Edmunda-karpa
    public static int maxFlow2(int[][] graph, int source, int sink, int V) {
        int[][] residualGraph = new int[V][V];
        for (int i = 0; i < V; i++) {
            System.arraycopy(graph[i], 0, residualGraph[i], 0, V);
        }

        int[] parent = new int[V];
        int maxFlow = 0;

        while (dfs(residualGraph, source, sink, parent,V)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }
        return maxFlow;
    }
}