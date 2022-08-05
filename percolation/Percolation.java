/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: WeightedQuickUnionUF.java StdIn.java StdOut.java
 *
 *  This program models a percolation system using an n-by-n grid sites.
 *  The task of this program is to estimate a threshold value.
 *
 *  Percolation
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The {@code Percolation} class represents a <em>percolation model</em>.
 * It uses the classic <em>union</em> and <em>find</em> operations,
 * along with a <em>count</em> operation that returns the total number of sets.
 */
public class Percolation {
    private final int n;
    private final boolean[] isOpen;
    private final int topIndex;
    private final int bottomIndex;
    private final WeightedQuickUnionUF virtualOneSites;
    private final WeightedQuickUnionUF virtualTwoSites;

    private int totalSites;

    /**
     * Initialize an empty union-find data structure with {@code n * n}
     * elements {@code 1} through {@code n * n}.
     * Initially, each site is blocked.
     *
     * @param n the number of rows or columns
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(n + "should be greater than 0!");
        }
        this.n = n;
        totalSites = 0;
        isOpen = new boolean[n * n + 2];
        topIndex = 0;
        bottomIndex = n * n + 1;
        virtualOneSites = new WeightedQuickUnionUF(bottomIndex);
        virtualTwoSites = new WeightedQuickUnionUF(bottomIndex + 1);
        isOpen[0] = true;
        isOpen[bottomIndex] = true;

    }

    /**
     * Converts a 2D coordinate to 1D.
     *
     * @param row the value of row
     * @param col the value of col
     * @return 1D coordinate
     */
    private int index(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("either row or column is out of bounds.");
        }
        return (row - 1) * n + col;
    }

    /**
     * Opens the site (row, col) if it is not open already.
     *
     * @param row the value of row
     * @param col the value of col
     * @throws IllegalArgumentException unless both {@code 0 < row <= n}
     *                                  and {@code 0 < col <= n}
     */
    public void open(int row, int col) {
        int currentIndex = index(row, col);
        if (isOpen[currentIndex]) {
            return;
        }
        isOpen[currentIndex] = true;
        totalSites++;
        if (row == 1) {
            virtualOneSites.union(currentIndex, topIndex);
            virtualTwoSites.union(currentIndex, topIndex);
        }
        if (row == n) {
            virtualTwoSites.union(currentIndex, bottomIndex);
        }
        findNeighborUnion(row, col, row - 1, col);
        findNeighborUnion(row, col, row + 1, col);
        findNeighborUnion(row, col, row, col - 1);
        findNeighborUnion(row, col, row, col + 1);

    }

    /**
     * Connects all possible neighbor sites.
     *
     * @param r1 the row of the first site
     * @param c1 the column of the first site
     * @param r2 the row of the second site
     * @param c2 the column of the second site
     */
    private void findNeighborUnion(int r1, int c1, int r2, int c2) {
        if (r2 > 0 && r2 <= n && c2 > 0 && c2 <= n && isOpen(r2, c2)) {
            int currentIndex = index(r1, c1);
            int neighborIndex = index(r2, c2);
            virtualOneSites.union(currentIndex, neighborIndex);
            virtualTwoSites.union(currentIndex, neighborIndex);
        }
    }

    /**
     * Returns true if the site (row, col) is open.
     *
     * @param row the value of row
     * @param col the value of col
     * @return {@code true} if the site ({@code row}, {@code col}) is open
     * @throws IllegalArgumentException unless both {@code 0 < row <= n}
     *                                  and {@code 0 < col <= n}
     */
    public boolean isOpen(int row, int col) {
        int currentIndex = index(row, col);
        return isOpen[currentIndex];
    }

    /**
     * Returns true if the site (row, col) is full.
     *
     * @param row the value of row
     * @param col the value of col
     * @return {@code true} if the site ({@code row}, {@code col}) is full
     * @throws IllegalArgumentException unless both {@code 0 < row <= n}
     *                                  and {@code 0 < col <= n}
     */
    public boolean isFull(int row, int col) {
        int currentIndex = index(row, col);
        return virtualOneSites.find(topIndex) == virtualOneSites.find(currentIndex);
    }


    /**
     * Returns the number of open sites.
     *
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return totalSites;
    }

    /**
     * Returns true if the system percolate.
     *
     * @return {@code true} if the system percolate
     */
    public boolean percolates() {
        return virtualTwoSites.find(topIndex) == virtualTwoSites.find(bottomIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            // StdOut.println("row = " + row + ", col = " + col);
            percolation.open(row, col);
        }
        StdOut.println("The total number of open sites are " + percolation.numberOfOpenSites());

    }

}
