package Calculators;

import Matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

public class RibbonCalculator {
    public Matrix multiplyMatrix(Matrix matrix1, Matrix matrix2, int threadsCount) {
        validateMatrices(matrix1, matrix2);

        var height = matrix1.getRowsSize();
        var width = matrix2.getColumnsSize();
        var resultMatrix = new Matrix(height, width);

        var rowsPerThread = calculateRowsPerThread(height, threadsCount);
        var threads = createThreads(matrix1, matrix2, resultMatrix, threadsCount, rowsPerThread);
        startAndJoinThreads(threads);
        return resultMatrix;
    }

    private void validateMatrices(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsSize() != matrix2.getRowsSize()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied: " +
                    "the number of columns of matrix A is not equal to the number of rows of matrix B.");
        }
    }

    private int calculateRowsPerThread(int height, int threadsCount) {
        return Math.max(height / threadsCount, 1);
    }

    private List<Thread> createThreads(Matrix matrix1, Matrix matrix2, Matrix resultMatrix, int threadsCount,
                                       int rowsPerThread) {
        var threads = new ArrayList<Thread>();
        var height = matrix1.getRowsSize();
        var width = matrix2.getColumnsSize();

        for (var i = 0; i < threadsCount; i++) {
            var from = i * rowsPerThread;
            var to = (i == threadsCount - 1) ? height : (i + 1) * rowsPerThread;

            var thread = new Thread(() -> multiplyPartial(matrix1, matrix2, resultMatrix, from, to, width));
            threads.add(thread);
        }

        return threads;
    }

    private void multiplyPartial(Matrix matrix1, Matrix matrix2, Matrix resultMatrix, int startMatrix1, int endMatrix1,
                                 int widthMatrix2) {
        for (var rowIdM1 = startMatrix1; rowIdM1 < endMatrix1; rowIdM1++) {
            for (var columnIdM2 = 0; columnIdM2 < widthMatrix2; columnIdM2++) {
                var sum = 0;

                for (var k = 0; k < matrix2.getRowsSize(); k++) {
                    sum += matrix1.get(rowIdM1, k) * matrix2.get(k, columnIdM2);
                }

                resultMatrix.set(rowIdM1, columnIdM2, sum);
            }
        }
    }

    private void startAndJoinThreads(List<Thread> threads) {
        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}