package model.algorithm.geometry;

import app.model.algorithm.geometry.PlaneQuarterPartitioner;
import app.model.structure.Field;
import app.model.structure.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlaneQuarterPartitionerTest {

    Map<Integer, Node> nodes;
    PlaneQuarterPartitioner partitioner;

    @BeforeEach
    void setUp() {
        nodes = new HashMap<>();
        nodes.put(1, new Field(1, "Field", new Point(10, 10), 0));
        nodes.put(2, new Field(2, "Field", new Point(20, 20), 0));
        nodes.put(3, new Field(3, "Field", new Point(30, 30), 0));
        nodes.put(4, new Field(4, "Field", new Point(40, 40), 0));
        partitioner = new PlaneQuarterPartitioner(nodes);
    }

    @Test
    void testFindCenterPoint_SimpleCase() {
        Point center = partitioner.findCenterPoint();
        assertEquals(new Point(25, 25), center);
    }

    @Test
    void testAssignNodesToQuarters_EdgeAndBoundaryCases() {
        partitioner.assignNodesToQuarters();
        Map<Integer, List<Node>> quarterMap = partitioner.getQuarterMap();

        assertTrue(quarterMap.get(0).stream().anyMatch(n -> n.getPosition().equals(new Point(30, 30))));
        assertTrue(quarterMap.get(0).stream().anyMatch(n -> n.getPosition().equals(new Point(40, 40))));

        assertTrue(quarterMap.get(2).stream().anyMatch(n -> n.getPosition().equals(new Point(10, 10))));
        assertTrue(quarterMap.get(2).stream().anyMatch(n -> n.getPosition().equals(new Point(20, 20))));

        assertTrue(quarterMap.get(1).isEmpty());
        assertTrue(quarterMap.get(3).isEmpty());
    }

    @Test
    void testSetBarleyAmountForQuarters_BoundaryAndInvalidInput() {
        partitioner.assignNodesToQuarters();

        partitioner.setBarleyAmountForQuarters(0, 50);
        List<Node> q0Nodes = partitioner.getQuarterMap().get(0);
        for (Node node : q0Nodes) {
            if (node instanceof Field) {
                Field f = (Field) node;
                assertEquals(50, f.getBarleyAmount());
                assertEquals(50, f.getCurrentAmountOfBarleyInTheField());
            }
        }

        partitioner.setBarleyAmountForQuarters(2, 100);
        List<Node> q2Nodes = partitioner.getQuarterMap().get(2);
        for (Node node : q2Nodes) {
            if (node instanceof Field) {
                Field f = (Field) node;
                assertEquals(100, f.getBarleyAmount());
                assertEquals(100, f.getCurrentAmountOfBarleyInTheField());
            }
        }

        assertThrows(IllegalArgumentException.class, () -> partitioner.setBarleyAmountForQuarters(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> partitioner.setBarleyAmountForQuarters(4, 10));
    }

}
