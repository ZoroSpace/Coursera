package Week4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
/**
 * Created by Zoro on 17-7-12.
 */
public class Solver {
    private LinkedList<Board> closedList;
    private boolean solvableFlag;
    public Solver(Board initialBoard) {
        if (initialBoard == null) throw new IllegalArgumentException("The constructor received a null argument.");

        LinkedList<Board> twinClosedList;
        Board twinInitialBoard = initialBoard.twin();
        closedList = new LinkedList<>();
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
            if (!currentNeighbors.isEmpty()) {
                currentBoard = currentNeighbors.delMin();
            }
            if (currentBoard.isGoal()) {
                closedList.add(currentBoard);
                solvableFlag = true;
                break;
            }


            if ((!twinClosedList.contains(twinCurrentBoard))) {
                twinClosedList.add(twinCurrentBoard);
                twinCurrentNeighbors = (MinPQ)twinCurrentBoard.neighbors();
            }
            if (!twinCurrentNeighbors.isEmpty()) {
                twinCurrentBoard = twinCurrentNeighbors.delMin();
            }
            if (twinCurrentBoard.isGoal()) {
                twinClosedList.add(twinCurrentBoard);
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
        else return closedList.size() - 1;
    }

    public static void main(String[] args) {

        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);

        int[][] blocks1 = new int[2][2];
        blocks1[0][0] = 1;
        blocks1[0][1] = 2;
        blocks1[1][0] = 0;
        blocks1[1][1] = 3;
        Board board1 = new Board(blocks1);
        int[][] blocks3 = new int[3][3];
        blocks3[0][0] = 5;
        blocks3[0][1] = 6;
        blocks3[0][2] = 2;
        blocks3[1][0] = 1;
        blocks3[1][1] = 8;
        blocks3[1][2] = 4;
        blocks3[2][0] = 7;
        blocks3[2][1] = 3;
        blocks3[2][2] = 0;
        Board board3 = new Board(blocks3);
        // solve the puzzle
        Solver solver = new Solver(board3);

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
