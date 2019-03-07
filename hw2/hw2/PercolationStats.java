package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private PercolationFactory percolation;
    private double [] fractionsOpen;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 && T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        percolation = pf;
        fractionsOpen = new double[T];

    }   // perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(fractionsOpen);
    }                                          // sample mean of percolation threshold
   public double stddev() {
        return StdStats.stddev(fractionsOpen);
   }                                       // sample standard deviation of percolation threshold
   public double confidenceLow() {
        return mean() - (1.96 * stddev()) / fractionsOpen.length;
   }                                // low endpoint of 95% confidence interval
   public double confidenceHigh() {
       return mean() + (1.96 * stddev()) / fractionsOpen.length;

   }                               // high endpoint of 95% confidence interval
}
