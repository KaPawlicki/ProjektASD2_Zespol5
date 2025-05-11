package app.util;

import app.model.structure.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    private ShireMap shireMap;

    public DataLoader(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    public void loadFromFile(String fileName) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            // wczytaj wszystkie wierzcholki (Fields, Breweries, Inns)
            int numberOfNodes = Integer.parseInt(line); // liczba wszystkich obiektów
            for(int i=0; i < numberOfNodes; i++){
                line = br.readLine();
                String[] parts = line.split(" ");
                String type = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);

                switch(type){
                    case "Pole":
                        int barleyAmount = Integer.parseInt(parts[3]);
                        Field field = new Field(i, type, new Point(x,y), barleyAmount);
                        shireMap.addNode(field);
                        break;
                    case "Browar":
                        Brewery brewery = new Brewery(i, type, new Point(x,y));
                        shireMap.addNode(brewery);
                        break;
                    case "Karczma":
                        Inn inn = new Inn(i, type, new Point(x,y));
                        shireMap.addNode(inn);
                        break;
                    case "Skrzyżowanie":
                        Intersection intersection = new Intersection(i, type, new Point(x,y));
                        shireMap.addNode(intersection);
                        break;
                }
            }
            // Wczytujemy wszystkie krawedzie
            line = br.readLine();
            int numberOfEdges = Integer.parseInt(line);
            for(int i=0; i < numberOfEdges; i++){
                line = br.readLine();
                String[] parts = line.split(" ");
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                int capacity = Integer.parseInt(parts[2]);
                int repairCost = Integer.parseInt(parts[3]);
                Edge edge = new Edge(from, to, capacity, repairCost);
                shireMap.addEdge(edge);
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("Error file loading" + e.getMessage());
        }
    }
}
