package Week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Zoro on 17-7-12.
 */

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        Board currentBoard;
        int steps;
        SearchNode previousNode;

        public SearchNode(Board currentBoard, int steps, SearchNode previousNode) {
            this.currentBoard = currentBoard;
            this.steps = steps;
            this.previousNode = previousNode;
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.steps + this.currentBoard.manhattan() <
                    that.steps + that.currentBoard.manhattan()) return -1;
            if (this.steps + this.currentBoard.manhattan() >
                    that.steps + that.currentBoard.manhattan()) return 1;
            return 0;
        }
    }
    private boolean solvableFlag = true;
    private Stack solution = new Stack();

    public Solver(Board initialBoard) {
        if (initialBoard == null)
            throw new IllegalArgumentException("The constructor received a null argument.");
        MinPQ<SearchNode> openList = new MinPQ<>();
        SearchNode initialSearchNode = new SearchNode(initialBoard,0,null);
        openList.insert(initialSearchNode);
        SearchNode end = null;//CLOSED
        while (!openList.min().currentBoard.isGoal()) {
            SearchNode currentNode = openList.delMin();
            //add current to CLOSED
            end = currentNode;
            label:
            for (Board board : currentNode.currentBoard.neighbors()) {
                board.step = currentNode.steps + 1;
                //if neighbor in OPEN and cost less than g(neighbor):
                for (SearchNode node : openList) {
                    if (board.equals(node.currentBoard)) {
                        if (board.step < node.steps) {
                            node.steps = board.step;
                            node.previousNode = currentNode;
                        }
                        continue label;
                    }
                }
                //if neighbor in CLOSED and cost less than g(neighbor):
                for (SearchNode node = end;node != null;node = node.previousNode) {
                    if (board.equals(node.currentBoard)) {
                        if (board.step < node.steps) {
                            node.steps = board.step;
                            node.previousNode = currentNode;
                        }
                        continue label;
                    }
                }
                //if neighbor not in OPEN and neighbor not in CLOSED:
                openList.insert(new SearchNode(board,board.step,currentNode));
            }
        }
        end = openList.min();
        for (SearchNode node = end;node != null;node = node.previousNode) {
            solution.push(node.currentBoard);
        }
    }

    public boolean isSolvable() {
        return solvableFlag;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        } else {
            return solution;
        }
    }

    public int moves() {
        if (!isSolvable()) return -1;
        else return solution.size() - 1;
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
