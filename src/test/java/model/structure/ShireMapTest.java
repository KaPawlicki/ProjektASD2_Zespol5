package model.structure;

import app.model.structure.Edge;
import app.model.structure.Field;
import app.model.structure.Inn;
import app.model.structure.ShireMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShireMapTest {

    private ShireMap map;

    @BeforeEach
    public void setUp() {
        map = new ShireMap();
    }

    @Test
    public void testIsEmptyInitially() {
        assertTrue(map.isEmpty());
    }

    @Test
    public void testAddNodeIncreasesNodeCount() {
        Field field = new Field(1, "Pole", new Point(0, 0), 100);
        map.addNode(field);

        assertEquals(1, map.getNumberOfNodes());
        assertFalse(map.isEmpty());
        assertSame(field, map.getNode(1));
    }

    @Test
    public void testAddEdgeIncreasesEdgeCount() {
        Field field = new Field(1, "Pole", new Point(0, 0), 100);
        Inn inn = new Inn(2, "Karczma", new Point(1, 1));

        map.addNode(field);
        map.addNode(inn);

        Edge edge = new Edge(1, 2, 5, 10);
        map.addEdge(edge);

        assertEquals(1, map.getNumberOfEdges());
        assertTrue(field.getOutgoingEdges().contains(edge));
        assertTrue(inn.getIncomingEdges().contains(edge));
    }

    @Test
    public void testAddEdgeWithMissingNodeDoesNothing() {
        Field field = new Field(1, "Pole", new Point(0, 0), 100);
        map.addNode(field);

        Edge edge = new Edge(1, 2, 5, 10); // node 2 not added
        map.addEdge(edge);

        assertEquals(0, map.getNumberOfEdges());
    }

    @Test
    public void testClearRemovesAllData() {
        map.addNode(new Field(1, "Pole", new Point(0, 0), 100));
        map.addNode(new Inn(2, "Karczma", new Point(1, 1)));
        map.addEdge(new Edge(1, 2, 5, 10));

        map.clear();

        assertEquals(0, map.getNumberOfNodes());
        assertEquals(0, map.getNumberOfEdges());
        assertTrue(map.isEmpty());
    }

    @Test
    public void testFindMaxId() {
        map.addNode(new Field(10, "Pole", new Point(0, 0), 100));
        map.addNode(new Inn(5, "Karczma", new Point(1, 1)));

        assertEquals(10, map.findMaxId());
    }
}
