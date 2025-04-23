package app.model;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ShireMap {
    private final Map<Integer, Node> nodes = new HashMap<>(); // lista wszystkich obiektów (pola, browary, karczmy)
    private int numberOfNodes;
    private int numberOfEdges;

    public ShireMap() {
        numberOfNodes = 0;
        numberOfEdges = 0;
    }

    public void addNode(Node n) {
        nodes.put(n.getId(), n);
        numberOfNodes = nodes.size();
    }

    public void addEdge(Edge e) {
        Node fromNode = nodes.get(e.getFrom());
        Node toNode = nodes.get(e.getTo());

        if (fromNode != null) {
            fromNode.addOutgoingEdge(e);
            numberOfEdges++;
        }

        if (toNode != null) {
            toNode.addIncomingEdge(e);
        }
    }

    public int getNumberOfEdges() { return numberOfEdges; }
    public int getNumberOfNodes() { return numberOfNodes; }
    public Node getNode(int id) {
        return nodes.get(id);
    }
    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public void print() {
        for(Node n : nodes.values()) {
            System.out.println(n);
        }
    }


    private int[][] createCapacityMatrix() { //metoda tworzaca macierz pojemnosci dla sieci przeplywowej
        int n = nodes.size();
        int[][] capacity = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(capacity[i], 0);
        }

        for (Node node : nodes.values()) {
            for (Edge edge : node.getOutgoingEdges()) {
                capacity[edge.getFrom()][edge.getTo()] = edge.getCapacity();
            }
        }

        return capacity;
    }

    public int calculateFieldToBreweryFlow() { //  maksymalnY przepływu z pól do browarów
        List<Integer> sources = new ArrayList<>(); //dodanie do listy ID pol jako zrodel
        for (Node node : nodes.values()) {
            if (node instanceof Field) {
                sources.add(node.getId());
            }
        }


        List<Integer> sinks = new ArrayList<>();  // dodanie do listy ID browarów jako ujś
        for (Node node : nodes.values()) {
            if (node instanceof Brewery) {
                sinks.add(node.getId());
            }
        }


        int[][] capacity = createCapacityMatrix();

        return EdmondsKarp.maxFlow(capacity, sources, sinks);
    }


    public int calculateBreweryToInnFlow() { // obliczanie maksymalnego przepływu z browarów do karczm
        // Zbieramy ID browarów jako źródła
        List<Integer> sources = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (node instanceof Brewery) {
                sources.add(node.getId());
            }
        }


        List<Integer> sinks = new ArrayList<>();  // dodanie do listy ID karczm jako ujś
        for (Node node : nodes.values()) {
            if (node instanceof Inn) {
                sinks.add(node.getId());
            }
        }

        int[][] capacity = createCapacityMatrix();

        return EdmondsKarp.maxFlow(capacity, sources, sinks);
    }

    public void simulateWholeProcess() {

        int maxBarleyFlow = calculateFieldToBreweryFlow(); //przeplyw w sieci pola->browary
        System.out.println("Maksymalny przepływ jęczmienia z pól do browarów: " + maxBarleyFlow + " ton");

        int totalBeerProduced = 0;
        List<Brewery> breweries = new ArrayList<>();

        for (Node node : nodes.values()) {
            if (node instanceof Brewery) {
                breweries.add((Brewery) node);
            }
        }

        int remainingBarley = maxBarleyFlow;
        for (Brewery brewery : breweries) { //produkcja piiiiwa
            if (remainingBarley > 0) {
                int barleyToProcess = remainingBarley;
                int beerProduced = brewery.processBarley(barleyToProcess);
                remainingBarley -= barleyToProcess;
                totalBeerProduced += beerProduced;
            }
        }
        System.out.println("Całkowita ilość wyprodukowanego piwa: " + totalBeerProduced + " jednostek");

        int maxBeerFlow = calculateBreweryToInnFlow(); //przeplyw w sieci browary->karczmy
        System.out.println("Maksymalny przepływ piwa z browarów do karczm: " + Math.min(maxBeerFlow, totalBeerProduced) + " jednostek");

    }
}