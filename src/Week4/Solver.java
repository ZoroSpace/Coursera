package Week4;

import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by Zoro on 17-7-12.
 */
public class Solver {
    SearchNode searchNode;
    public Solver(Board initial) {
        searchNode = new SearchNode(initial,0,null);
        MinPQ<SearchNode> minpq = new MinPQ<>();
        minpq.insert(searchNode);

    }

    public boolean isSolvable() {
        return true;//TODO
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

    class SearchNode {
        Board board;
        int step;
        SearchNode next;

        public SearchNode(Board board, int step, SearchNode next) {
            this.board = board;
            this.step = step;
            this.next = next;
        }
    }
}
