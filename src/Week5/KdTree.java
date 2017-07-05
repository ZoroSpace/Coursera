package Week5;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by Zoro on 17-7-3.
 */
public class KdTree {
    private PointNode root;
    private class PointNode {
        private Point2D point2D;
        private PointNode left, right;
        private boolean isVertical;
        private int size;
        private PointNode (Point2D p,boolean isVertical) {
            this.point2D = p;
            this.isVertical = isVertical;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(PointNode x) {
        if (x == null) return 0;
        else return x.size;
    }


    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(this.root,p) != null;
    }

    private Point2D get(PointNode nodeX, Point2D p) {
        if (nodeX == null) return null;
        if (p.x() == nodeX.point2D.x() && p.y() == nodeX.point2D.y()) {
            return p;
        }
        if (nodeX.isVertical) {
            if (p.x() <= nodeX.point2D.x()) {
                return get(nodeX.left,p);
            } else {
                return get(nodeX.right,p);
            }
        } else {
            if (p.y() <= nodeX.point2D.y()) {
                return get(nodeX.left,p);
            } else {
                return get(nodeX.right,p);
            }
        }
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called insert() with a null key");
        root = insert(root,p,true);
    }

    private PointNode insert(PointNode nodeX,Point2D p,boolean isVertical) {
        if (nodeX == null) {
            PointNode newNode = new PointNode(p,isVertical);
            newNode.size = 1;
            return newNode;
        }
        if (p.x() == nodeX.point2D.x() && p.y() == nodeX.point2D.y()) return nodeX;

        if (nodeX.isVertical) {
            if (p.y() == nodeX.point2D.y()) {
                if (p.x() < nodeX.point2D.x()) {
                    nodeX.left = insert(nodeX.left,p,true);
                } else {
                    nodeX.right = insert(nodeX.right,p,true);
                }
            } else {
                if (p.x() <= nodeX.point2D.x()) {
                    nodeX.left = insert(nodeX.left,p,false);
                } else {
                    nodeX.right = insert(nodeX.right,p,false);
                }
            }
        } else {
            if (p.x() == nodeX.point2D.x()) {
                if (p.y() < nodeX.point2D.y()) {
                    nodeX.left = insert(nodeX.left,p,false);
                } else {
                    nodeX.right = insert(nodeX.right,p,false);
                }
            } else {
                if (p.y() <= nodeX.point2D.y()) {
                    nodeX.left = insert(nodeX.left,p,true);
                } else {
                    nodeX.right = insert(nodeX.right,p,true);
                }
            }

        }
        nodeX.size = 1 + size(nodeX.left) + size(nodeX.right);
        return nodeX;
    }

    public void draw() {
        draw(root,0,1,0,1);
    }

    private void draw(PointNode nodeX,double minX,double maxX,double minY,double maxY) {
        if (nodeX == null) return;
        if (nodeX.isVertical) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(nodeX.point2D.x(),minY,nodeX.point2D.x(),maxY);
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            nodeX.point2D.draw();
            draw(nodeX.left,minX,nodeX.point2D.x(),minY,maxY);
            draw(nodeX.right,nodeX.point2D.x(),maxX,minY,maxY);
        } else {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX,nodeX.point2D.y(),maxX,nodeX.point2D.y());
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            nodeX.point2D.draw();
            draw(nodeX.left,minX,maxX,minY,nodeX.point2D.y());
            draw(nodeX.right,minX,maxX,nodeX.point2D.y(),maxY);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("called range() with a null key");
        return range(rect,root,0,1,0,1);
    }

    private Queue<Point2D> range(RectHV rect,PointNode nodeX, double minX,double maxX,double minY,double maxY) {
        if (nodeX == null) return null;
        Queue<Point2D> queue = new Queue<>();
        if (rect.intersects(new RectHV(minX,minY,maxX,maxY))) {
            if (rect.contains(nodeX.point2D)) queue.enqueue(nodeX.point2D);
            Queue<Point2D> subQueue;
            if (nodeX.isVertical) {
                subQueue = range(rect, nodeX.left, minX, nodeX.point2D.x(), minY, maxY);
                if (subQueue != null) {
                    for (Point2D p : subQueue) queue.enqueue(p);
                }
                subQueue = range(rect, nodeX.right, nodeX.point2D.x(), maxX, minY, maxY);
                if (subQueue != null) {
                    for (Point2D p : subQueue) queue.enqueue(p);
                }
            } else {
                subQueue = range(rect, nodeX.left, minX, maxX, minY, nodeX.point2D.y());
                if (subQueue != null) {
                    for (Point2D p : subQueue) queue.enqueue(p);
                }
                subQueue = range(rect, nodeX.right, minX, maxX, nodeX.point2D.y(), maxY);
                if (subQueue != null) {
                    for (Point2D p : subQueue) queue.enqueue(p);
                }
            }
            return queue;
        } else return null;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to nearest() is null");
        if (root == null) return null;
        return nearest(p,root,root.point2D,0,1,0,1);
    }

    private Point2D nearest(Point2D queryPoint, PointNode nodeX,Point2D nearestPoint,double minX,double maxX,double minY,double maxY) {
        if (nodeX == null) return nearestPoint;
        if (nearestPoint.distanceTo(queryPoint) < (new RectHV(minX,minY,maxX,maxY)).distanceTo(queryPoint)) return nearestPoint;
        if (nearestPoint.distanceTo(queryPoint) > nodeX.point2D.distanceTo(queryPoint)) nearestPoint = nodeX.point2D;

        if (nodeX.isVertical) {
            if ((new RectHV(minX,minY,nodeX.point2D.x(),maxY)).contains(queryPoint)) {
                nearestPoint = nearest(queryPoint, nodeX.left, nearestPoint, minX, nodeX.point2D.x(), minY, maxY);
                nearestPoint = nearest(queryPoint, nodeX.right, nearestPoint, nodeX.point2D.x(), maxX, minY, maxY);
            } else {
                nearestPoint = nearest(queryPoint, nodeX.right, nearestPoint, nodeX.point2D.x(), maxX, minY, maxY);
                nearestPoint = nearest(queryPoint, nodeX.left, nearestPoint, minX, nodeX.point2D.x(), minY, maxY);
            }
        } else {
            if ((new RectHV(minX,minY,maxX,nodeX.point2D.y())).contains(queryPoint)) {
                nearestPoint = nearest(queryPoint, nodeX.left, nearestPoint, minX, maxX, minY, nodeX.point2D.y());
                nearestPoint = nearest(queryPoint, nodeX.right, nearestPoint, minX, maxX, nodeX.point2D.y(), maxY);
            } else {
                nearestPoint = nearest(queryPoint, nodeX.right, nearestPoint, minX, maxX, nodeX.point2D.y(), maxY);
                nearestPoint = nearest(queryPoint, nodeX.left, nearestPoint, minX, maxX, minY, nodeX.point2D.y());
            }
        }
        return nearestPoint;
    }
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7,0.5));
        kdTree.insert(new Point2D(0.7,0.3));
        kdTree.insert(new Point2D(0.6,0.3));
        kdTree.insert(new Point2D(0.5,0.3));

        kdTree.insert(new Point2D(0.7,0.2));
        kdTree.insert(new Point2D(0.5,0.4));
        kdTree.insert(new Point2D(0.9,0.6));
        kdTree.insert(new Point2D(0.2,0.3));
        kdTree.insert(new Point2D(0.4,0.7));
        kdTree.draw();
        System.out.println(kdTree.nearest(new Point2D(0.7,0.6)));
//        System.out.println(kdTree.range(new RectHV(0.7,0.5,0.7,0.5)));
//        System.out.println(kdTree.contains(new Point2D(0.7,0.5)));
//        System.out.println(kdTree.contains(new Point2D(0.7,0.3)));
//        System.out.println(kdTree.contains(new Point2D(0.6,0.3)));
//        System.out.println(kdTree.contains(new Point2D(0.5,0.3)));

//        System.out.println(kdTree.contains(new Point2D(0.7,0.2)));
//        System.out.println(kdTree.contains(new Point2D(0.5,0.4)));
//        System.out.println(kdTree.contains(new Point2D(0.9,0.6)));
//        System.out.println(kdTree.contains(new Point2D(0.2,0.3)));
//        System.out.println(kdTree.contains(new Point2D(0.4,0.7)));
//        System.out.println(kdTree.contains(new Point2D(0.4,0.3)));
    }
}
