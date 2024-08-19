package Experiments;

import Calculators.Fox.FoxCalculator;
import Calculators.RibbonCalculator;
import Matrix.Matrix;
import Matrix.MatrixGenerator;

public class ThreadsCountExperiments {
    final static int MATRIX_SIZE = 1300;
    final static int THREADS_COUNT = 32;

    public static void main(String[] args) {
        var matrixGenerator = new MatrixGenerator();
        long startTime;
        long endTime;

        var matrix1 = matrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        var matrix2 = matrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        var ribbonCalculator = new RibbonCalculator();
        var foxCalculator = new FoxCalculator(matrix1, matrix2, THREADS_COUNT);

        startTime = System.currentTimeMillis();
        var ribbonResult = ribbonCalculator.multiplyMatrix(matrix1, matrix2, THREADS_COUNT);
        endTime = System.currentTimeMillis();
        System.out.println("Ribbon result: " + (endTime - startTime) + " ms " + "with " + THREADS_COUNT + " threads");

        startTime = System.currentTimeMillis();
        var foxResult = foxCalculator.multiplyMatrix();
        endTime = System.currentTimeMillis();
        System.out.println("Fox result: " + (endTime - startTime) + " ms " + "with " + THREADS_COUNT + " threads");

        validateCalculationResults(ribbonResult, foxResult);
    }

    private static void validateCalculationResults(Matrix ribbonResult, Matrix foxResult) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (foxResult.get(i, j) != ribbonResult.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
