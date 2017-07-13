package Week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

/**
 * Created by Zoro on 17-7-12.
 */
public class Solver {
    private LinkedList<Board> closedList;
    private LinkedList<Board> twinClosedList;
    private Board initialBorad;
    int isSolvable = 0;
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("The constructor received a null argument.");
        }
        this.initialBorad = initial;
        closedList = new LinkedList<>();//closed list
        Board currentNode = initial;
        MinPQ<Board> currentNeighbors = (MinPQ)currentNode.neighbors();//open list
        while (!currentNode.isGoal()) {
            if ((new Solver(currentNode)).isSolvable() && (!closedList.contains(currentNode))) {
                closedList.add(currentNode);
                currentNeighbors = (MinPQ)currentNode.neighbors();
            }
            currentNode = currentNeighbors.delMin();
        }
    }

    public boolean isSolvable() {
        if (isSolvable == 1) {
            return true;
        } else if (isSolvable == -1) {
            return false;
        }
        return false;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        } else return null;//TODO
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        } else return 1;//TODO
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
