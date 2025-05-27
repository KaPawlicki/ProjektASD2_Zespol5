package app.model.structure;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Node {
    final int id;
    String type;
    Point position;
    List<Edge> outgoingEdges = new ArrayList<>();
    List<Edge> incomingEdges = new ArrayList<>();

    public Node(int id, String type, Point position) {
        this.id = id;
        this.type = type;
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }

    public void addOutgoingEdge(Edge e) {
        outgoingEdges.add(e);
    }

    public void addIncomingEdge(Edge e) {
        incomingEdges.add(e);
    }
}
