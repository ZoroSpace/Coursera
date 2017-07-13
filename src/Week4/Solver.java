package Week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Zoro on 17-7-12.
 */
public class Solver {
    private LinkedList<Board> closedList;
    HashSet<Board> caculatedBoard = new HashSet<>();//用于缓存计算过的Board TODO
    private boolean solvableFlag;
    public Solver(Board initialBoard) {

        if (initialBoard == null) {
            throw new IllegalArgumentException("The constructor received a null argument.");
        }
        LinkedList<Board> twinClosedList;
        Board twinInitialBoard = initialBoard.twin();
        closedList = new LinkedList<>();//closed list
        twinClosedList = new LinkedList<>();
        Board currentBoard = initialBoard;
        Board twinCurrentBoard = twinInitialBoard;
        MinPQ<Board> currentNeighbors = (MinPQ)currentBoard.neighbors();//open list
        MinPQ<Board> twinCurrentNeighbors = (MinPQ)twinCurrentBoard.neighbors();
        while (true) {
            if ((!closedList.contains(currentBoard))) {
                closedList.add(currentBoard);
                currentNeighbors = (MinPQ)currentBoard.neighbors();
            }
            currentBoard = currentNeighbors.delMin();

            if ((!twinClosedList.contains(twinCurrentBoard))) {
                twinClosedList.add(twinCurrentBoard);
                twinCurrentNeighbors = (MinPQ)twinCurrentBoard.neighbors();
            }
            twinCurrentBoard = twinCurrentNeighbors.delMin();
            if (currentBoard.isGoal()) {
                solvableFlag = true;
                break;
            }
            if (twinCurrentBoard.isGoal()) {
                solvableFlag = false;
                break;
            }
        }
    }

    public boolean isSolvable() {
        return solvableFlag;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        } else {
            return closedList;
        }
    }

    public int moves() {
        if (!isSolvable()) return -1;
        else return closedList.size();
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
