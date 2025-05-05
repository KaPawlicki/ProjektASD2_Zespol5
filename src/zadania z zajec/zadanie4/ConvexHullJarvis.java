import java.util.ArrayList;
import java.util.List;

public class ConvexHullJarvis {

    static Point p0;

    public int orientation(Point p, Point q, Point r) {
        int crossProduct = (q.x - p.x) * (r.y - p.y) -
                (q.y - p.y) * (r.x - p.x);

        if (crossProduct < 0) return -1; // clockwise
        if (crossProduct > 0) return 1;  // counter-clockwise
        return 0;                        // collinear
    }

    List<Point> points;

    public List<Point> ConvexHullJarvis(List<Point> points) {

        List<Point> hull = new ArrayList<>();

        // 1. Find bottommost point
        p0 = points.getFirst();
        int p0Index = 0;
        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            if (p.y < p0.y || (p.y == p0.y && p.x < p0.x)) {
                p0 = p;
                p0Index = i;
            }
        }

        // Start form bottommost point, keep moving counterclockwise
        int p = p0Index, q;
        do {
            hull.add(points.get(p));

            q = (p + 1) % points.size();
            for (int i = 0; i < points.size(); i++) {
                if (orientation(points.get(p),
                        points.get(i),
                        points.get(q)) == 1) {
                    q = i;
                }
            }

            p = q;
        } while (p != p0Index);

        return hull;
    }

}
