package app.model.structure;

import java.awt.*;

public class Intersection extends Node {

    public Intersection(int id, String type, Point position) {
        super(id, type, position);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "outgoingEdges=" + outgoingEdges +
                ", incomingEdges=" + incomingEdges +
                '}';
    }
}
