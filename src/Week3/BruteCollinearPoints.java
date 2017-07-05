package Week3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private int n = 0;
    private List<LineSegment> line = new ArrayList<>();
    private Point[] fourPoints = new Point[4];

    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new NullPointerException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length-3; i++) {
            fourPoints[0] = points[i];
            for (int j = i+1; j < points.length-2; j++) {
                fourPoints[1] = points[j];
                for (int k = j+1; k < points.length-1; k++) {
                    fourPoints[2] = points[k];
                    for (int l = k+1; l < points.length; l++) {
                        fourPoints[3] = points[l];
                        if (Math.abs(points[l].slopeTo(points[k])-points[l].slopeTo(points[j])) < 0.000001
                                && Math.abs(points[l].slopeTo(points[k])-points[l].slopeTo(points[i])) < 0.000001) {
                            Arrays.sort(fourPoints);
                            n++;
                            line.add(new LineSegment(fourPoints[0], fourPoints[3]));
                        } else if (points[l].slopeTo(points[i]) == Double.POSITIVE_INFINITY &&
                                points[l].slopeTo(points[j]) == Double.POSITIVE_INFINITY &&
                                points[l].slopeTo(points[k]) == Double.POSITIVE_INFINITY) {
                            Arrays.sort(fourPoints);
                            n++;
                            line.add(new LineSegment(fourPoints[0], fourPoints[3]));
                        }
                    }
                }
            }
        }

    }   // finds all line segments containing 4 points
    public int numberOfSegments() {
        return n;
    }       // the number of line segments
    public LineSegment[] segments() {
        return line.toArray(new LineSegment[line.size()]);
    }               // the line segments
    public static void main(String[] args) {
//        Point[] points = {new Point(10,0),new Point(0,10),new Point(3,7),new Point(7,3),
//                new Point(20,21),new Point(3,4),new Point(14,15),new Point(6,7)};
//        Point[] points = {new Point(10,0),new Point(0,10),new Point(3,7),new Point(7,3)};
//        FastCollinearPoints bruteCollinearPoints = new FastCollinearPoints(points);
//        LineSegment[] lineSegments = bruteCollinearPoints.segments();
//        for (int i = 0; i < lineSegments.length; i++) {
//            System.out.println(lineSegments[i]);
//        }
//        System.out.println(bruteCollinearPoints.numberOfSegments());
    }
}