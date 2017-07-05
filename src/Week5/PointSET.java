import edu.princeton.cs.algs4.*;
/**
 * Created by Zoro on 17-7-2.
 */
public class PointSET {
    private SET<Point2D> pointSet;
    public PointSET() {
        pointSet = new SET<>();
    }
    public boolean isEmpty() {
        return pointSet.size() == 0;
    }
    public int size() {
        return pointSet.size();
    }
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called insert() with a null key");
        if (!pointSet.contains(p)) {
            pointSet.add(p);
        }
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called contains() with a null key");
        return pointSet.contains(p);
    }
    public void draw() {
        StdDraw.setPenRadius(0.02);
        for (Point2D p : pointSet) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("called range() with a null key");
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called nearest() with a null key");
        if (pointSet.isEmpty()) return null;
        Point2D minPoint = pointSet.min();
        for (Point2D o : pointSet) {
            if (p.distanceTo(minPoint) > p.distanceTo(o)) {
                minPoint = o;
            }
        }
        return minPoint;
    }
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0.1,0.2));
        pointSET.insert(new Point2D(0.2,0.3));
        pointSET.insert(new Point2D(0.3,0.4));
        System.out.println(pointSET.isEmpty());
        System.out.println(pointSET.size());
        pointSET.draw();
        RectHV rectHV = new RectHV(0.15,0.1,0.4,0.5);
        System.out.println(pointSET.range(rectHV));
        System.out.println(pointSET.nearest(new Point2D(0,0)));
    }
}
