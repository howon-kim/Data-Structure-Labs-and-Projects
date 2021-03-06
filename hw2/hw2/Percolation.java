package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean [][] grid;
    private WeightedQuickUnionUF openness;
    private WeightedQuickUnionUF connect;
    private int numOpen;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            grid = new boolean[N][N];
            openness = new WeightedQuickUnionUF(N * N + 2);
            connect = new WeightedQuickUnionUF(N * N + 1);
            numOpen = 0;
        }
    }

    public void open(int row, int col) {
        checkIndexBound(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOpen += 1;
        }
        if (row == 0) {
            openness.union(grid.length * grid.length, gridTo2D(row, col));
            connect.union(grid.length * grid.length, gridTo2D(row, col));
        }
        if (row == grid.length - 1) {
            openness.union(grid.length * grid.length + 1, gridTo2D(row, col));
        }
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            openness.union(gridTo2D(row, col), gridTo2D(row - 1, col));
            connect.union(gridTo2D(row, col), gridTo2D(row - 1, col));

        }
        if (row + 1 < grid.length && isOpen(row + 1, col)) {
            openness.union(gridTo2D(row, col), gridTo2D(row + 1, col));
            connect.union(gridTo2D(row, col), gridTo2D(row + 1, col));

        }
        if (col + 1 < grid.length && isOpen(row, col + 1)) {
            openness.union(gridTo2D(row, col), gridTo2D(row, col + 1));
            connect.union(gridTo2D(row, col), gridTo2D(row, col + 1));

        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            openness.union(gridTo2D(row, col), gridTo2D(row, col - 1));
            connect.union(gridTo2D(row, col), gridTo2D(row, col - 1));

        }
    }
    public boolean isOpen(int row, int col) {
        checkIndexBound(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        checkIndexBound(row, col);
        return connect.connected(grid.length * grid.length, gridTo2D(row, col));
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return openness.connected(grid.length * grid.length, grid.length * grid.length + 1);
    }

    public static void main(String[] args) {
    }

    // Helper Function //
    private void checkIndexBound(int row, int col) {
        if (row >= grid.length && col >= grid.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int gridTo2D(int row, int col) {
        return (row * grid.length) + col;
    }
}
