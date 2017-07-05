package Week1;
import Week1.Percolation;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Zoro on 17-4-21.
 */
public class PercolationStats {
    private int[] x;
    private int trials;
    private int row;
    private int col;
    private int index;
    private int n;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        x = new int[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                index = StdRandom.uniform(n*n)+1;
                if ((index % n) == 0) {
                    row = index/n;
                    col = n;
                } else {

                    col = index % n;
                    row = index/n + 1;
                }
                percolation.open(row, col);
            }
            x[i] = percolation.numberOfOpenSites();
        }
    }   // perform trials independent experiments on an n-by-n grid
    public double mean() {
        return StdStats.mean(x)/(n*n);
    }                         // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(x)/(n*n);
    }                       // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }                 // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(n);
        int trials = Integer.parseInt(args[1]);
        System.out.println(trials);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo()
                + ", " + percolationStats.confidenceHi() + "]");


    }       // test client (described below)
}
