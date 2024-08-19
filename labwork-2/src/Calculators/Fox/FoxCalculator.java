package Calculators.Fox;

import Matrix.Matrix;

public class FoxCalculator {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int threadsCount;
    private final Matrix resultMatrix;

    public FoxCalculator(Matrix matrix1, Matrix matrix2, int threadsCount) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = new Matrix(matrix1.getRowsSize(), matrix2.getColumnsSize());

        var maxThreads = matrix1.getRowsSize() * matrix2.getColumnsSize() / 4;
        this.threadsCount = Math.min(Math.max(threadsCount, 1), maxThreads);
    }

    public Matrix multiplyMatrix() {
        var step = (int) Math.ceil((double) matrix1.getRowsSize() / (int) Math.sqrt(threadsCount));
        var threads = createAndStartThreads(step);
        waitForThreadsCompletion(threads);
        return resultMatrix;
    }

    private FoxCalculatorThread[] createAndStartThreads(int blockSize) {
        var threads = new FoxCalculatorThread[threadsCount];
        var threadIndex = 0;

        for (var i = 0; i < matrix1.getRowsSize(); i += blockSize) {
            for (var j = 0; j < matrix2.getColumnsSize(); j += blockSize) {
                if (threadIndex >= threadsCount) {
                    System.err.println("Warning: More blocks than threads, some threads will handle multiple blocks.");
                    break;
                }

                threads[threadIndex] = new FoxCalculatorThread(matrix1, matrix2, i, j, blockSize, resultMatrix);
                threads[threadIndex].start();
                threadIndex++;
            }
        }

        return threads;
    }

    private void waitForThreadsCompletion(FoxCalculatorThread[] threads) {
        for (var thread : threads) {
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}
