package model.algorithm.geometry;

import app.model.algorithm.geometry.ConvexHull;
import app.model.structure.Node;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvexHullTest {

    // Pomocnicza klasa do testów, implementująca Node
    static class TestNode extends Node {
        public TestNode(int id, String type, Point position) {
            super(id, type, position);
        }
    }

    @Test
    void testConvexHullWith20Points() {
        List<Node> nodes = new ArrayList<>();

        // 20 punktów - przykładowe współrzędne, z których utworzymy otoczkę
        nodes.add(new TestNode(1, "node", new Point(1, 1)));
        nodes.add(new TestNode(2, "node", new Point(2, 5)));
        nodes.add(new TestNode(3, "node", new Point(3, 3)));
        nodes.add(new TestNode(4, "node", new Point(5, 3)));
        nodes.add(new TestNode(5, "node", new Point(3, 1)));
        nodes.add(new TestNode(6, "node", new Point(7, 2)));
        nodes.add(new TestNode(7, "node", new Point(6, 6)));
        nodes.add(new TestNode(8, "node", new Point(5, 7)));
        nodes.add(new TestNode(9, "node", new Point(2, 7)));
        nodes.add(new TestNode(10, "node", new Point(1, 4)));
        nodes.add(new TestNode(11, "node", new Point(4, 4)));
        nodes.add(new TestNode(12, "node", new Point(4, 2)));
        nodes.add(new TestNode(13, "node", new Point(8, 4)));
        nodes.add(new TestNode(14, "node", new Point(9, 5)));
        nodes.add(new TestNode(15, "node", new Point(7, 7)));
        nodes.add(new TestNode(16, "node", new Point(8, 6)));
        nodes.add(new TestNode(17, "node", new Point(6, 4)));
        nodes.add(new TestNode(18, "node", new Point(7, 5)));
        nodes.add(new TestNode(19, "node", new Point(5, 5)));
        nodes.add(new TestNode(20, "node", new Point(3, 6)));

        ConvexHull ch = new ConvexHull(nodes);
        List<Point> hull = ch.createConvexHull();

        // Sprawdźmy, że otoczka zawiera oczekiwane punkty (wierzchołki)
        // Najprościej jest sprawdzić, że wszystkie punkty otoczki są z oryginalnej listy
        for (Point p : hull) {
            boolean found = nodes.stream().anyMatch(n -> n.getPosition().equals(p));
            assertTrue(found, "Otoczka zawiera punkt spoza oryginalnych danych: " + p);
        }

        // Wstępna asercja - otoczka powinna mieć minimum 5 punktów (w tym przykładzie)
        assertTrue(hull.size() >= 5, "Otoczka powinna mieć co najmniej 5 punktów");

        // Możesz też wypisać otoczkę dla debugowania
        System.out.println("Convex hull points:");
        hull.forEach(p -> System.out.println(p.x + ", " + p.y));
    }
}
