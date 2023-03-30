package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
import java.util.TreeSet;

public class Percolation {
    private final int N;
    private final WeightedQuickUnionUF uf;
    private final TreeSet<Integer> openSet;
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("N should be greater than or equal to 0");
        }
        this.N = N;
        // Create a WeightedQuickUnionUF
        uf = new WeightedQuickUnionUF(N * N + 2);
        openSet = new TreeSet<>();
    }               // create N-by-N grid, with all sites initially blocked
    public void open(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        openSet.add(index);
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] direction : directions) {
            int neighbor_x = row + direction[0];
            int neighbor_y = col + direction[1];
            int neighbor = xyTo1D(neighbor_x, neighbor_y);
            if (neighbor_x>= 0 && neighbor_x<N && neighbor_y>=0 && neighbor_y<N) {
                if (isOpen(neighbor_x, neighbor_y)) {
                    uf.union(index, neighbor);
                }
            }
        }
        // Create two virtual helper point (top and bottom)
        if (row == 0) {
            uf.union(index, N*N);
        }
//        else if (row == N-1) {
//            uf.union(index, N*N+1);
//        }
    }     // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSet.contains(xyTo1D(row, col));
    } // is the site (row, col) open?
    public boolean isFull(int row, int col) {
        return uf.connected(xyTo1D(row, col), N*N);
    }  // is the site (row, col) full?
    public int numberOfOpenSites() {
        return openSet.size();
    }
    public boolean percolates() {
        for (int index = N*(N-1); index < N * N; index++) {
            if (uf.connected(N * N, index)) {
                return true;
            }
        }
        return false;
    }             // does the system percolate?

    private void validate(int row, int col) {
        if (row<0 || row>=N || col<0|| col>=N) {
            System.out.println("ROW: "+row+", COL: "+col);
            throw new IndexOutOfBoundsException("Row or column index is out of bounds.");
        }
    }
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }
    public int[][] getStatus() {
        int[][] status = new int[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (isOpen(row, col)) {
                    if (isFull(row, col)) {
                        status[row][col] = 2;
                    } else {
                        status[row][col] = 1;
                    }
                } else {
                    status[row][col] = 0;
                }
            }
        }
        return status;
    }
}
