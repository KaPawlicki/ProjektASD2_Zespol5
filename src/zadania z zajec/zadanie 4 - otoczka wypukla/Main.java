import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] points = new int[n];

        //List<Point> points = Arrays
        */

        List<Point> points = Arrays.asList(
                new Point(0, 3),
                new Point(1, 1),
                new Point(2, 2),
                new Point(4, 4),
                new Point(0, 0),
                new Point(1, 2),
                new Point(3, 1),
                new Point(3, 3)
        );

        ConvexHullGraham convexHull = new ConvexHullGraham();
        ArrayList<Point> hull = convexHull.ConvexHullGraham(points);

        System.out.println("Punkty wypukłej otoczki(Graham):");
        for (Point p : hull) {
            System.out.println(p);
        }

        ConvexHullJarvis jarvis = new ConvexHullJarvis();
        List<Point> jarv = jarvis.ConvexHullJarvis(points);

        System.out.println("Punkty wypukłej otoczki(Jarvis):");
        for (Point p : hull) {
            System.out.println(p);
        }
    }

}