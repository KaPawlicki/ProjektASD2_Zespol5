package app.model;

import java.util.HashMap;
import java.util.Map;

public class ShireMap {
    private final Map<Integer, Node> nodes = new HashMap<>(); // lista wszystkich obiektów (pola, browary, karczmy)
    private int numberOfNodes = 0;
    private int numberOfEdges = 0;

    private final NetworkFlowManager flowManager;
    private final SimulationEngine simulationEngine;
    private final StateManager stateManager;

    public ShireMap() {
        numberOfNodes = 0;
        numberOfEdges = 0;

        // inicjalizacja zależnych klas
        this.flowManager = new NetworkFlowManager(this);
        this.stateManager = new StateManager(this);
        this.simulationEngine = new SimulationEngine(this);
    }


    public void addNode(Node n){
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
        for (Node n : nodes.values()) {
            System.out.println(n);
        }
    }

    public boolean isNotEmpty(){
        return numberOfNodes != 0; // dla testow wystarczy przynajmniej sam wierzcholek potem do sprawdzenia
    }

    public void clear(){
        nodes.clear();
        numberOfNodes = 0;
        numberOfEdges = 0;
    }

    public int findMaxId() {
        int maxId = 0;
        for (Integer id : nodes.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId;
    }

    public void simulateWholeProcess() {
        simulationEngine.simulateWholeProcess();
    }

    public void simulateWholeProcessWithActivation() {
        simulationEngine.simulateWholeProcessWithActivation();
    }
}