package hw2;

import java.util.ArrayList;
import java.util.Random;

public class PercolationStats {
    private final int N;
    private final int T;
    private Percolation pc;
    private double[] thresholds;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        this.thresholds = new double[T];

        ArrayList samplesList = new ArrayList();
        for (int i = 0; i < N * N; i++) {
            samplesList.add(i);
        }
        // Use MC simulation to iterate until the system is percolated.
        Random rand = new Random();
        for (int iterNum = 0; iterNum < T; iterNum++) {
            this.pc = pf.make(N);
            for (int i = 0; i < N * N; i++) {
                int sampleIndex = rand.nextInt(N * N);
                int sampleRow = sampleIndex / N;
                int sampleCol = sampleIndex % N;
                pc.open(sampleRow, sampleCol);
                if (pc.percolates()) {
                    thresholds[iterNum] = (double) pc.numberOfOpenSites() / (N * N);
                    break;
                }
            }
        }
    }
    public double mean() {
        double totalNum = 0.;
        for (double currentNum : thresholds) {
            totalNum = totalNum + currentNum;
        }
        return totalNum / T;
    }
    public double stddev() {
        double totalDevSquare = 0.;
        double mean = mean();
        for (double i : thresholds) {
            totalDevSquare = totalDevSquare + Math.pow((i - mean), 2);
        }
        return Math.sqrt(totalDevSquare / (T - 1));
    }
    public double confidenceLow() {
        double mean = mean();
        double stddev = stddev();
        return (mean - 1.96 * stddev / Math.sqrt(T));
    }
    public double confidenceHigh() {
        double mean = mean();
        double stddev = stddev();
        return (mean + 1.96 * stddev / Math.sqrt(T));
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(10, 20, pf);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
