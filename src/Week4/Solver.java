package Week4;

import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by Zoro on 17-7-12.
 */
public class Solver {
    public Solver(Board initial) {
        SearchNode serachNode = new SearchNode(initial,0,null);
        MinPQ<SearchNode> minpq = new MinPQ<>();
        minpq.insert(serachNode);

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
