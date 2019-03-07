package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private Percolation percolation;
    private double [] fractionsOpen;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        fractionsOpen = new double[T];

        for (int i = 0; i < T; i++) {
            percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            fractionsOpen[i] = percolation.numberOfOpenSites() / Math.pow(N, 2);
        }
    }   // perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(fractionsOpen);
    }                                          // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionsOpen);
    }                                       // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(fractionsOpen.length);
    }                                // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(fractionsOpen.length);
    }                               // high endpoint of 95% confidence interval
}
