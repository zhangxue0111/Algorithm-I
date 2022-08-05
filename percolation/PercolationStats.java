import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE = 1.96;
    private final double[] results;

    /**
     * Perform independent trials on an n-by-n grid.
     *
     * @param n      the number of row or column
     * @param trials the number of trails
     * @throws IllegalArgumentException if either {@code n <= 0} or {@code trials <= 0}
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("both n and trails should be greater than 0");
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                percolation.open(row, col);
            }
            results[i] = 1.0 * percolation.numberOfOpenSites() / (n * n);
        }
    }

    /**
     * Sample mean of percolation threshold.
     *
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * Sample standard deviation of percolation threshold.
     *
     * @return the sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * Low endpoint of 95% confidence interval.
     *
     * @return the low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - CONFIDENCE * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE * stddev() / Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("there should be at least three arguments");
        }
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trails);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" +
                               percolationStats.confidenceLo() + ", "
                               + percolationStats.confidenceHi() + "]");

    }
}
