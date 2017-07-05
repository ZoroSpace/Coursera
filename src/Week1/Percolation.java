package Week1;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Zoro on 17-4-19.
 */
public class Percolation {
    private int n;
    private int openedSites = 0;
    private boolean[] flags;
    private WeightedQuickUnionUF lattices;
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        flags = new boolean[n*n+2];
        flags[0] = true;
        flags[n*n+1] = true;
        lattices = new WeightedQuickUnionUF(n*n + 2);
    }                // create n-by-n grid, with all sites blocked
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            openedSites++;
            flags[(row - 1) * n + col] = true;
            if (n == 1 && row == n && col == n) {
                lattices.union(0, 1);
                lattices.union(1, 2);
            } else if (row == 1) {
                lattices.union(0, (row - 1) * n + col);
                if (isOpen(row+1, col)) {
                    lattices.union((row - 1) * n + col, row * n + col);
                }
            } else if (row == n) {
                lattices.union((row - 1) * n + col, n*n + 1);
                if (isOpen(row-1, col)) {
                    lattices.union((row - 1) * n + col, (row - 2) * n + col);
                }
            } else if (col == 1) {
                if (isOpen(row-1, col)) {
                    lattices.union((row - 1) * n + col, (row - 2) * n + col);
                }
                if (isOpen(row+1, col)) {
                    lattices.union((row - 1) * n + col, row * n + col);
                }
                if (isOpen(row, col + 1)) {
                    lattices.union((row - 1) * n + col, (row - 1) * n + col + 1);
                }
            } else if (col == n) {
                if (isOpen(row-1, col)) {
                    lattices.union((row - 1) * n + col, (row - 2) * n + col);
                }
                if (isOpen(row+1, col)) {
                    lattices.union((row - 1) * n + col, row * n + col);
                }
                if (isOpen(row, col - 1)) {
                    lattices.union((row - 1) * n + col, (row - 1) * n + col - 1);
                }
            } else {
                if (isOpen(row-1, col)) {
                    lattices.union((row - 1) * n + col, (row - 2) * n + col);
                }
                if (isOpen(row+1, col)) {
                    lattices.union((row - 1) * n + col, row * n + col);
                }
                if (isOpen(row, col - 1)) {
                    lattices.union((row - 1) * n + col, (row - 1) * n + col - 1);
                }
                if (isOpen(row, col + 1)) {
                    lattices.union((row - 1) * n + col, (row - 1) * n + col + 1);
                }
            }
        }
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        validates(row, col);
        return flags[(row - 1) * n + col];

    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        validates(row, col);
        return lattices.connected(0, (row - 1) * n + col);
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return openedSites;

    }      // number of open sites
    public boolean percolates() {

        return lattices.connected(0, n * n + 1);
    }             // does the system percolate?

    private void validates(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException("index " + row + " is not between 1 and " + n);
        }
        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException("index " + col + " is not between 1 and " + n);
        }
    }
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readByte();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }
        System.out.println(percolation.isFull(9, 6));
    }  // test client (optional)
}
