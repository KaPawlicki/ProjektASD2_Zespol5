package SourceCode;

import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ShireMap map = new ShireMap();
        Node f1 = new Field("field", new Point(1, 2), 20);
        map.addNode(f1);
        Node f2 = new Field("field", new Point(1, 3), 20);
        map.addNode(f2);
        Node b1 = new Brewery("brewery", new Point(1, 4), 50);
        map.addNode(b1);
        Node i1 = new Inn("inn", new Point(1, 5));
        map.addNode(i1);
        map.print();
        map.addEdge(f1, new Edge(f1.getId(), f2.getId(), 10, 0));
        map.print();

    }
}