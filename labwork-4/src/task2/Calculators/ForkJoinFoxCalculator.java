package task2.Calculators;

import java.util.concurrent.ForkJoinPool;
import task2.Matrix.Matrix;

public class ForkJoinFoxCalculator {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int threadsCount;
    private final Matrix resultMatrix;

    public ForkJoinFoxCalculator(Matrix matrix1, Matrix matrix2, int threadsCount) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = new Matrix(matrix1.getRowsSize(), matrix2.getColumnsSize());

        int maxThreads = matrix1.getRowsSize() * matrix2.getColumnsSize() / 4;
        this.threadsCount = Math.min(Math.max(threadsCount, 1), maxThreads);
    }

    public Matrix multiplyMatrix() {
        var step = (int) Math.ceil((double) matrix1.getRowsSize() / (int) Math.sqrt(threadsCount));

        try (var pool = new ForkJoinPool(threadsCount)) {
            System.out.println("Threads count: " + pool.getParallelism());

            for (var i = 0; i < matrix1.getRowsSize(); i += step) {
                for (var j = 0; j < matrix2.getColumnsSize(); j += step) {
                    pool.invoke(new ForkJoinFoxCalculatorTask(matrix1, matrix2, i, j, step, resultMatrix));
                }
            }
        }

        return resultMatrix;
    }
}
