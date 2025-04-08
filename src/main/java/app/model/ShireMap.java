package app.model;


import java.util.HashMap;
import java.util.Map;

public class ShireMap {
    private final Map<Integer,Node> nodes = new HashMap<>(); // lista wszystkich obiektów (pola, browary ,karczmy)

    public void addNode(Node n){
        nodes.put(n.getId(), n);
    }

    public void addEdge(Node n, Edge e) {
        nodes.get(n.getId()).addEdge(e);
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public void print() {
        for(Node n : nodes.values()) {
            System.out.println(n);
        }
    }
}
