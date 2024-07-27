package task2;

import task2.Calculators.ForkJoinFoxCalculator;
import task2.Calculators.SequentialCalculator;
import task2.Matrix.Matrix;
import task2.Matrix.MatrixGenerator;

public class Main {
    final static int MATRIX_SIZE = 1000;
    final static int THREADS_COUNT = 8;

    public static void main(String[] args) {
        var matrixGenerator = new MatrixGenerator();
        long startTime;
        long endTime;

        var matrix1 = matrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        var matrix2 = matrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        var sequentialCalculator = new SequentialCalculator();
        var foxCalculator = new ForkJoinFoxCalculator(matrix1, matrix2, THREADS_COUNT);
        System.out.println("Calculating...");

        startTime = System.currentTimeMillis();
        var result1 = sequentialCalculator.multiplyMatrix(matrix1, matrix2);
        endTime = System.currentTimeMillis();
        System.out.println("Sequential result: " + (endTime - startTime) + " ms ");

        startTime = System.currentTimeMillis();
        var result2 = foxCalculator.multiplyMatrix();
        endTime = System.currentTimeMillis();
        System.out.println("Fox result: " + (endTime - startTime) + " ms ");

        validateCalculationResults(result1, result2);
    }

    private static void validateCalculationResults(Matrix matrix1, Matrix matrix2) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix2.get(i, j) != matrix1.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
