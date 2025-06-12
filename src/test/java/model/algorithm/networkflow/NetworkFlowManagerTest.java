package model.algorithm.networkflow;

import app.model.algorithm.networkflow.NetworkFlowManager;
import app.model.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NetworkFlowManagerTest {

    private ShireMap map;
    private NetworkFlowManager manager;

    @BeforeEach
    void setUp() {
        map = new ShireMap();

        Field field = new Field(1, "Pole", new Point(0, 0), 10);
        Brewery brewery = new Brewery(2, "Browar", new Point(1, 0));
        Inn inn = new Inn(3, "Karczma", new Point(2, 0));

        map.addNode(field);
        map.addNode(brewery);
        map.addNode(inn);

        map.addEdge(new Edge(1, 2, 10, 3)); // Pole -> Browar
        map.addEdge(new Edge(2, 3, 10, 5)); // Browar -> Karczma

        brewery.setCurrentAmountOfBeerProduced(8);

        manager = new NetworkFlowManager(map);
    }

    @Test
    void testCreateCapacityMatrix() {
        int[][] matrix = manager.createCapacityMatrix();
        assertEquals(10, matrix[1][2]); // Pole -> Browar
        assertEquals(10, matrix[2][3]); // Browar -> Karczma
    }

    @Test
    void testCreateActivationCostMatrix() {
        int[][] matrix = manager.createActivationCostMatrix();
        assertEquals(3, matrix[1][2]); // koszt naprawy z pola do browaru
        assertEquals(5, matrix[2][3]); // koszt naprawy z browaru do karczmy
        assertEquals(0, matrix[1][1]); // koszt własny
        assertEquals(Integer.MAX_VALUE, matrix[3][1]); // brak połączenia
    }

    @Test
    void testCalculateFieldToBreweryFlow() {
        int flow = manager.calculateFieldToBreweryFlow();
        assertEquals(10, flow); // cały jęczmień może trafić do browaru
    }

    @Test
    void testCalculateBreweryToInnFlow() {
        int flow = manager.calculateBreweryToInnFlow();
        assertEquals(8, flow); // browar ma tylko 8 piwa
    }

    @Test
    void testPrepareActivationSimulationData() {
        Map<String, Object> data = manager.prepareActivationSimulationData();

        List<Integer> fields = (List<Integer>) data.get("fieldIds");
        List<Integer> breweries = (List<Integer>) data.get("breweryIds");
        List<Integer> inns = (List<Integer>) data.get("innIds");
        Map<Integer, Integer> capacities = (Map<Integer, Integer>) data.get("fieldCapacities");

        assertEquals(List.of(1), fields);
        assertEquals(List.of(2), breweries);
        assertEquals(List.of(3), inns);
        assertEquals(10, capacities.get(1));
    }

    @Test
    void testCapacityMatrixHasNoExtraEdges() {
        int[][] matrix = manager.createCapacityMatrix();

        // Sprawdzamy, że nie ma przepustowości między niepołączonymi wierzchołkami
        assertEquals(0, matrix[1][3]);
        assertEquals(0, matrix[3][1]);
        assertEquals(0, matrix[3][2]);
    }

    @Test
    void testActivationCostMatrixDiagonal() {
        int[][] matrix = manager.createActivationCostMatrix();

        // Koszt naprawy na przekątnej powinien być 0
        assertEquals(0, matrix[1][1]);
        assertEquals(0, matrix[2][2]);
        assertEquals(0, matrix[3][3]);
    }

    @Test
    void testActivationCostMatrixNonConnected() {
        int[][] matrix = manager.createActivationCostMatrix();

        // Sprawdzamy, że brak połączenia ma koszt Integer.MAX_VALUE
        assertEquals(Integer.MAX_VALUE, matrix[3][1]);
        assertEquals(Integer.MAX_VALUE, matrix[3][2]);
        assertEquals(Integer.MAX_VALUE, matrix[1][3]);
    }

    @Test
    void testFlowNotExceedingCapacity() {
        int fieldToBreweryFlow = manager.calculateFieldToBreweryFlow();
        int breweryToInnFlow = manager.calculateBreweryToInnFlow();

        // Sprawdzenie, że przepływ z pola do browaru nie przekracza pojemności krawędzi (10)
        assertTrue(fieldToBreweryFlow <= 10);

        // Przepływ z browaru do karczmy nie przekracza produkcji piwa (8)
        assertTrue(breweryToInnFlow <= 8);
    }


}
