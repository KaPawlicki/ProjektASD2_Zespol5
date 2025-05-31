package app.model.algorithm.geometry;

import app.model.structure.Field;
import app.model.structure.Node;


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaneQuarterPartitioner {
    private final Map<Integer, Node> nodes;
    private final Map<Integer, List<Node>> quarterMap; // listy wierzchołków poszczególnych ćwiartek
    private final Map<Integer, List<Point>> boundaryPointsQuarters; // listy współrzędnych punktów otoczki wypukłej dla kazdej ćwiartki
    private Point centerPoint;

    public Map<Integer, List<Node>> getQuarterMap() {
        return quarterMap;
    }

    public Map<Integer, List<Point>> getBoundaryPointsQuarters() {
        return boundaryPointsQuarters;
    }

    public PlaneQuarterPartitioner(Map<Integer, Node> nodes) {
        this.nodes = nodes;
        this.quarterMap = new HashMap<>();
        this.boundaryPointsQuarters = new HashMap<>();
        for(int i = 0; i < 4; i++) {
            quarterMap.put(i, new ArrayList<>());
            boundaryPointsQuarters.put(i, new ArrayList<>());
        }
    }
    

    public Point findCenterPoint() {
        double sumX = 0, sumY = 0;
        for(Node node : nodes.values()) {
            sumX += node.getPosition().getX();
            sumY += node.getPosition().getY();
        }
        return new Point((int) (sumX / nodes.size()), (int) (sumY / nodes.size()));
    }

    public void assignNodesToQuarters(){
        quarterMap.values().forEach(List::clear); // Resetujemy zawartość ćwiartek
        centerPoint = findCenterPoint();
        for(Node node : nodes.values()) {
            Point pos = node.getPosition();
            int id;
            if (pos.x >= centerPoint.x && pos.y >= centerPoint.y) id = 0;
            else if (pos.x < centerPoint.x && pos.y >= centerPoint.y) id = 1;
            else if (pos.x < centerPoint.x && pos.y < centerPoint.y) id = 2;
            else id = 3;
            this.quarterMap.get(id).add(node);
        }
    }

    public void checkBoundaryPointsForQuarters() {
        for(int i = 0; i < 4; i++){
            // jeżeli brakuję punktów do utworzenia otoczki to dodajemy odpowiednią ilość w zależności od tego ile punktów znajduje się w boundaryPointsQuaters
            if(quarterMap.get(i).size() < 3) {
                // Określenie znaku x/y dla ćwiartki
                int xSign = (i == 0 || i == 3) ? 1 : -1;
                int ySign = (i == 0 || i == 1) ? 1 : -1;

                // Bazowy punkt w ćwiartce (10 jednostek od środka)
                int baseX = centerPoint.x + xSign * 10;
                int baseY = centerPoint.y + ySign * 10;
                if(quarterMap.get(i).size() == 2) {
                    quarterMap.get(i).add(new Field(-1, "", new Point(baseX, baseY), 0));
                }
                else if(quarterMap.get(i).size() == 1) {
                    quarterMap.get(i).add(new Field(-1, "", new Point(baseX, baseY), 0));
                    quarterMap.get(i).add(new Field(-1, "", new Point(baseX + 5 * xSign, baseY), 0));
                }
                else {
                    quarterMap.get(i).add(new Field(-1, "", new Point(baseX, baseY), 0));
                    quarterMap.get(i).add(new Field(-1, "", new Point(baseX + 5 * xSign, baseY), 0));
                    quarterMap.get(i).add(new Field(-1, "", new Point(baseX, baseY + 5 * ySign), 0));
                }
            }
        }
    }

    public void createPolygonForQuarters(){
        boundaryPointsQuarters.clear();
        checkBoundaryPointsForQuarters();
        for(int i = 0; i < 4; i++){
            ConvexHull convexHull = new ConvexHull(quarterMap.get(i));
            this.boundaryPointsQuarters.put(i, convexHull.createConvexHull());
        }
    }

    public void setBarleyAmountForQuarters(int quarter, double multiplier){
        if(quarter < 0 || quarter > 3) throw new IllegalArgumentException("Quarter must be between 0 and 3");
        List<Node> quarterNodes = quarterMap.get(quarter);
        for(Node node : quarterNodes) {
            if(node instanceof Field){
                Field f = (Field) node;
                f.setBarleyAmount((int) Math.round( (double) f.getBarleyAmount() * multiplier));
            }
        }
    }

}
