import java.util.*;

public class ConvexHullGraham {

    static Point p0;

    // Obliczenie orientacji punktow
    public int orientation(Point p, Point q, Point r) {
        int crossProduct = (q.x - p.x) * (r.y - p.y) -
                           (q.y - p.y) * (r.x - p.x);

        if (crossProduct < 0) return -1; // clockwise
        if (crossProduct > 0) return 1;  // counter-clockwise
        return 0; // collinear
    }

    static int distSq(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) +
               (p1.y - p2.y) * (p1.y - p2.y);
    }

    int compare(Point p1, Point p2) {
        int o = orientation(p0, p1, p2);
        if (o == 0) {
            return ((distSq(p0, p1) <= distSq(p0, p2)) ? -1 : 1);
        }

        return (o == 1) ? -1 : 1;
    }

    public ArrayList<Point> ConvexHullGraham(List<Point> points) {

        // 1. Znajdz punkt poczatkowy
        p0 = points.getFirst();
        int p0Index = 0;
        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            if (p.y < p0.y || (p.y == p0.y && p.x < p0.x)) {
                p0 = p;
                p0Index = i;
            }
        }

        // swap
        Point temp = points.get(p0Index);
        points.set(p0Index, points.getFirst());
        points.set(0, temp);

        // 2. Posortuj punkty wedlug kata wzgledem p0
        List<Point> sortedPoints = new ArrayList<>(points.subList(1, points.size()));
        sortedPoints.sort(this::compare);

        System.out.println();
        System.out.println("Punkty po przesortowaniu");
        for (Point p : sortedPoints) {
            System.out.println(p);
        }

        sortedPoints.add(0, p0);

        System.out.println();
        System.out.println("Punkty po dodaniu p0");
        for (Point p : sortedPoints) {
            System.out.println(p);
        }

        // 3. ?
        for (int i = 1; i < sortedPoints.size(); i++) {
            if (i < sortedPoints.size() - 1 && orientation(p0, sortedPoints.get(i), sortedPoints.get(i + 1)) == 0) {
                sortedPoints.remove(i);
                i--;
            }
        }

        System.out.println();
        System.out.println("Punkty bez linearncyh");
        for (Point p : sortedPoints) {
            System.out.println(p);
        }

        // 4. Przejdz przez posortowane punkty, tworzac otoczke wypukla na stosie
        /*
            - Dodaj p0 na stos.
            - Dla kazdego kolejnego sprawdzaj czy robi obrot w lewo
                - Jesli nie => usun ostatni punkt ze stosu
            - Dodaj nowy punkt
        */
        Stack<Point> stack = new Stack<>();
        stack.push(sortedPoints.get(0));
        stack.push(sortedPoints.get(1));
        stack.push(sortedPoints.get(2));

        System.out.println();

        for (int i = 3; i < sortedPoints.size(); i++) {
            while (stack.size() > 1 && orientation(stack.get(stack.size()-2), stack.peek(), sortedPoints.get(i)) != 1) {
                stack.pop();
                System.out.println("pop");
            }
            stack.push(sortedPoints.get(i));
            System.out.println("push");
        }

        // 5. Wynikiem jest stos ktory zawiera punkty otoczki wypuklej w kolejnosci przeciwnej do ruchu wskazowek zegara
        return new ArrayList<>(stack);
    }


}