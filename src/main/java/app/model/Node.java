package app.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private static int nextID = 0;
    private final int id;
    private String type;
    private Point position; // pozycja jest reprezentowana jako punkt na płaszczyźnie w postaci P = (x, y)
    protected List<Edge> outgoingEdges; // lista krawędzi wychodzących z wierzchołka
    protected List<Edge> incomingEdges; // lista krawędzi wchodzących do wierzchołka

    public Node(String type, Point position) {
        this.id = nextID;
        this.type = type;
        this.position = position;
        outgoingEdges = new ArrayList<>();
        incomingEdges = new ArrayList<>();
        nextID++;
    }

    public int getId() {return id;}
    public String getType() {return type;}
    public Point getPosition() {return position;}
    public List<Edge> getOutgoingEdges() {return outgoingEdges;}
    public List<Edge> getIncomingEdges() {return incomingEdges;}

    public void setType(String type) {this.type = type;}
    public void setPosition(Point position) {this.position = position;}
    public void setPosition(int x, int y) {this.position = new Point(x, y);}

    public void addOutgoingEdge(Edge e) {outgoingEdges.add(e);}
    public void addIncomingEdge(Edge e) {incomingEdges.add(e);}
}