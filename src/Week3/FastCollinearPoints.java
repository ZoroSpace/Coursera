package Week3;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int firstCounter;
    private int secondCounter;
    private DoubleCounters doubleCounters;
    private ArrayList<DoubleCounters> doubleCountersArray = new ArrayList<>();
    private ArrayList<Point> endPoints = new ArrayList<>();
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
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
        Point[] copyOfPoints = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            copyOfPoints[i] = points[i];
        }
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copyOfPoints, points[i].slopeOrder());
            for (int j = 0; j < points.length-1; j++) {

                if (Math.abs(points[i].slopeTo(copyOfPoints[j]) - points[i].slopeTo(copyOfPoints[j+1])) > 0.00000001) {
                    if (secondCounter - firstCounter > 1) {
                        doubleCounters = new DoubleCounters();
                        doubleCounters.firstCounter = firstCounter;
                        doubleCounters.secondCounter = secondCounter;
                        doubleCountersArray.add(doubleCounters);
                    }
                    firstCounter = j+1;
                    secondCounter = j+1;
                } else {
                    secondCounter++;
                }
                if ((j == points.length-2) && (secondCounter - firstCounter > 1)) {
                    doubleCounters = new DoubleCounters();
                    doubleCounters.firstCounter = firstCounter;
                    doubleCounters.secondCounter = secondCounter;
                    doubleCountersArray.add(doubleCounters);
                    firstCounter = 0;
                    secondCounter = 0;
                }

            }

            for (int j = 0; j < doubleCountersArray.size(); j++) {
                Point[] tempPoints = new Point[doubleCountersArray.get(j).secondCounter
                        - doubleCountersArray.get(j).firstCounter + 2];
                int l = 0;
                for (int k = doubleCountersArray.get(j).firstCounter; k < doubleCountersArray.get(j).secondCounter + 1; k++) {
                    tempPoints[l] = copyOfPoints[k];
                    l++;
                }
                tempPoints[l] = copyOfPoints[0];
                Arrays.sort(tempPoints);
                endPoints.add(tempPoints[0]);
                endPoints.add(tempPoints[l]);
            }
            doubleCountersArray.clear();
        }
        for (int i = 0; i < endPoints.size() - 2; i = i + 2) {
            for (int j = i + 2; j < endPoints.size(); j = j + 2) {
                if (endPoints.get(i).slopeTo(endPoints.get(j)) == Double.NEGATIVE_INFINITY &&
                        endPoints.get(i + 1).slopeTo(endPoints.get(j +1)) == Double.NEGATIVE_INFINITY) {
                    endPoints.remove(j);
                    endPoints.remove(j);
                    j = j - 2;
                }
            }
        }
        lineSegments = new LineSegment[endPoints.size()/2];
        for (int i = 0; i < lineSegments.length; i++) {
            lineSegments[i] = new LineSegment(endPoints.get(i * 2), endPoints.get(i * 2 + 1));
        }
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lineSegments.length;
    }       // the number of line segments
    public LineSegment[] segments() {
        return lineSegments;
    }               // the line segments
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
class DoubleCounters {
    int firstCounter;
    int secondCounter;
}