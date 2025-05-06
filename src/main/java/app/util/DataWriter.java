package app.util;

import app.model.structure.Edge;
import app.model.structure.Field;
import app.model.structure.Node;
import app.model.structure.ShireMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    private final ShireMap shireMap;

    public DataWriter(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    public void writeToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(shireMap.getNumberOfNodes() + "\n"); // zapisujemy liczbe wierzcholkow
            for(Node n : shireMap.getNodes().values()){ // zapisujemy poszczegolne wierzcholki
                String type = n.getType();
                writer.write(type + " ");
                writer.write(((int)(n.getPosition().getX())) + " " + (int)(n.getPosition().getY()) + " ");
                switch (type) {
                    case "Pole":
                        writer.write(((Field)n).getBarleyAmount() + "");
                        break;
                    case "Browar", "Skrzyżowanie", "Karczma":
                        break;
                    default:
                        throw new RuntimeException();
                }
                writer.write("\n");
            }
            writer.write(shireMap.getNumberOfEdges() + "\n"); // zapisujemy liczbe krawedzi
            for(Node n : shireMap.getNodes().values()){ // zapisujemy kolejno wszystkie krawedzie
                for(Edge e : n.getOutgoingEdges()){
                    writer.write(e.getFrom() + " " + e.getTo() + " " + e.getCapacity() + " " + e.getRepairCost() + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}
