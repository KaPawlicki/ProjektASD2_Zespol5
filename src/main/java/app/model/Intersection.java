package app.model;

import java.awt.*;

public class Intersection extends Node {
    public Intersection(String type, Point position) {
        super(type, position);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "outgoingEdges=" + outgoingEdges +
                ", incomingEdges=" + incomingEdges +
                '}';
    }
}
