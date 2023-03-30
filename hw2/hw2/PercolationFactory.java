package hw2;

import java.util.ArrayList;
import java.util.Random;

public class PercolationFactory {
    public Percolation make(int N) {
        return new Percolation(N);
    }
}
