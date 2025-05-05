package app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkFlowManager {
    private final ShireMap shireMap;

    public NetworkFlowManager(ShireMap shireMap) {
        this.shireMap = shireMap;
    }


    public int[][] createCapacityMatrix() {
        int n = shireMap.findMaxId();
        int[][] capacity = new int[n + 1][n + 1];

        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(capacity[i], 0);
        }

        for (Node node : shireMap.getNodes().values()) {
            for (Edge edge : node.getOutgoingEdges()) {
                capacity[edge.getFrom()][edge.getTo()] = edge.getCapacity();
            }
        }

        return capacity;
    }


    public int[][] createActivationCostMatrix() {
        int n = shireMap.findMaxId();
        int[][] activationCost = new int[n + 1][n + 1];
        int INF = Integer.MAX_VALUE;

        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(activationCost[i], INF);
            activationCost[i][i] = 0; // koszt przejścia do tego samego wierzchołka to 0
        }

        for (Node node : shireMap.getNodes().values()) {
            for (Edge edge : node.getOutgoingEdges()) {
                activationCost[edge.getFrom()][edge.getTo()] = edge.getRepairCost();
            }
        }

        return activationCost;
    }


    public int calculateFieldToBreweryFlow() {
        int maxId = shireMap.findMaxId();
        int n = maxId + 1;

        int superSource = n;
        int superSink = n + 1;

        // rozmiar macierzy to n + 2 (bo dodajemy superSource i superSink)
        int[][] extendedCapacity = new int[n + 2][n + 2];

        int[][] capacity = createCapacityMatrix();

        // kopiowanie istniejącej macierzy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                extendedCapacity[i][j] = capacity[i][j];
            }
        }

        // łączenie superSource z polami
        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Field) {
                Field field = (Field) node;
                int fieldId = field.getId();
                int barleyAvailable = field.getCurrentAmountOfBarleyInTheField();
                extendedCapacity[superSource][fieldId] = barleyAvailable;
            }
        }

        // łączenie browarów (Brewery) z superSink
        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Brewery) {
                int breweryId = node.getId();
                extendedCapacity[breweryId][superSink] = Integer.MAX_VALUE;
            }
        }

        return EdmondsKarp.maxFlow(extendedCapacity, superSource, superSink);
    }


    public int calculateBreweryToInnFlow() {
        int maxId = shireMap.findMaxId();
        int n = maxId + 1;

        int superSource = n;
        int superSink = n + 1;

        int[][] capacity = createCapacityMatrix();
        int[][] extendedCapacity = new int[n + 2][n + 2];

        // kopiowanie istniejącej macierzy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                extendedCapacity[i][j] = capacity[i][j];
            }
        }

        // łączenie superSource z browarami
        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Brewery) {
                Brewery brewery = (Brewery) node;
                int breweryId = brewery.getId();
                int beerAvailable = brewery.getCurrentAmountOfBeerProduced();
                extendedCapacity[superSource][breweryId] = beerAvailable;
            }
        }

        // łączenie karczm z superSink
        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Inn) {
                int innId = node.getId();
                extendedCapacity[innId][superSink] = Integer.MAX_VALUE;
            }
        }

        return EdmondsKarp.maxFlow(extendedCapacity, superSource, superSink);
    }
    //przygotowanie danych do symulacji
    public Map<String, Object> prepareActivationSimulationData() {
        Map<String, Object> data = new HashMap<>();

        List<Integer> fieldIds = new ArrayList<>();
        List<Integer> breweryIds = new ArrayList<>();
        List<Integer> innIds = new ArrayList<>();
        Map<Integer, Integer> fieldCapacities = new HashMap<>();

        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Field) {
                Field field = (Field) node;
                fieldIds.add(node.getId());
                fieldCapacities.put(node.getId(), field.getCurrentAmountOfBarleyInTheField());
            } else if (node instanceof Brewery) {
                breweryIds.add(node.getId());
            } else if (node instanceof Inn) {
                innIds.add(node.getId());
            }
        }

        data.put("fieldIds", fieldIds);
        data.put("breweryIds", breweryIds);
        data.put("innIds", innIds);
        data.put("fieldCapacities", fieldCapacities);

        return data;
    }
}