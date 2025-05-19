package app.model.algorithm.geometry;

import app.model.structure.Node;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ConvexHull {

    private Point p0;
    private List<Point> points;

    public ConvexHull(List<Node> nodes) {
        //sprawdzamy czy mamy wystarczajaco punktow aby utworzyc otoczke
        if(nodes.size() < 3){
            throw new IllegalArgumentException("Number of nodes must be at least 3");
        }
        this.points = new ArrayList<>();
        for(Node n : nodes) {
            this.points.add(new Point(n.getPosition()));
        }
        this.p0 = points.get(0);
    }

    // Obliczenie orientacji punktow
    public int orientation(Point p, Point q, Point r) {
        double crossProduct = (q.getX() - p.getX()) * (r.getY() - p.getY()) -
                (r.getX() - p.getX()) * (q.getY() - p.getY());

        if (crossProduct > 0) return 1;  // punkt q ma wiekszą wspolrzedną kątową niz r
        if (crossProduct < 0) return -1; // punkt q ma mniejsza współrzędną kątową niz r
        return 0; // punkty sa wspolliniowe z p0 czyli maja ta sama wspolrzedna katowa
    }

    private double distSq(Point p1, Point p2) {
        return (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) +
                (p1.getY() - p2.getY()) * (p1.getY() - p2.getY());
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) +
                (p2.y - p1.y) * (p2.y - p1.y));
    }

    private int compare(Point p1, Point p2) {
        int o = orientation(p0, p1, p2);
        if (o == 0) {
            return ((distSq(p0, p1) <= distSq(p0, p2)) ? -1 : 1);
        }

        return (o == 1) ? -1 : 1;
    }

    private void findP0(){
        for(Point p : points){
            if(p.getY() < p0.getY()){
                p0 = p;
            }
            else if(p.getY() == p0.getY()){
                if(p.getX() < p0.getX()){
                    p0 = p;
                }
            }
        }
    }

    public ArrayList<Point> createConvexHull() {
        // 1. znajdujemy punkt p0
        findP0();
        // 2. Posortuj punkty wedlug kata wzgledem p0
        points.sort(this::compare);

        /*
        System.out.println();
        System.out.println("Punkty po przesortowaniu");
        for (Point p : points) {
            System.out.println(p);
        }
        */
        // 3. Usuwamy wszystkie zbedne punkty wspoliniowe (zostawiamy tylko ten najdalej oddalony od p0)
        for (int i = 1; i < points.size() - 1;) {
            if (orientation(p0, points.get(i), points.get(i + 1)) == 0) {
                // usuń ten bliższy punkt
                if (distance(p0, points.get(i)) < distance(p0, points.get(i + 1))) {
                    points.remove(i);
                } else {
                    points.remove(i + 1);
                }
            } else {
                i++;
            }
        }
        //sprawdzamy czy mamy wystarczajaco punktow aby utworzyc otoczke
        if(this.points.size() < 3){
            throw new IllegalArgumentException("Number of nodes must be at least 3 with different angular coordinate");
        }
        /*
        System.out.println();
        System.out.println("Punkty bez linearncyh");
        for (Point p : points) {
            System.out.println(p);
        }
        */
        // 4. Przejdz przez posortowane punkty, tworzac otoczke wypukla na stosie
        /*
            - Dodaj p0 na stos.
            - Dla kazdego kolejnego sprawdzaj czy robi obrot w lewo
                - Jesli nie => usun ostatni punkt ze stosu
            - Dodaj nowy punkt
        */
        Stack<Point> stack = new Stack<>();
        stack.push(points.get(0));
        stack.push(points.get(1));
        stack.push(points.get(2));

        System.out.println();

        for (int i = 3; i < points.size(); i++) {
            while (stack.size() > 1 && orientation(stack.get(stack.size()-2), stack.peek(), points.get(i)) != 1) {
                stack.pop();
                //System.out.println("pop");
            }
            stack.push(points.get(i));
            //System.out.println("push");
        }

        // 5. Wynikiem jest stos ktory zawiera punkty otoczki wypuklej w kolejnosci przeciwnej do ruchu wskazowek zegara
        return new ArrayList<>(stack);
    }
}
