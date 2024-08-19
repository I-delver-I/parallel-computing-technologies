package Experiments;

import Calculators.Fox.FoxCalculator;
import Calculators.RibbonCalculator;
import Calculators.SequentialCalculator;
import Matrix.Matrix;
import Matrix.MatrixGenerator;

public class MatrixSizeExperiments {
    final static int MATRIX_SIZE = 500;
    final static int THREADS_COUNT = 4;

    public static void main(String[] args) {
        var randomMatrixGenerator = new MatrixGenerator();
        long startTime;
        long endTime;

        var matrix1 = randomMatrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        var matrix2 = randomMatrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        var sequentialCalculator = new SequentialCalculator();
        var ribbonCalculator = new RibbonCalculator();
        var foxCalculator = new FoxCalculator(matrix1, matrix2, THREADS_COUNT);

        startTime = System.currentTimeMillis();
        var sequentialResult = sequentialCalculator.multiplyMatrix(matrix1, matrix2);
        endTime = System.currentTimeMillis();
        System.out.println("Sequential result: " + (endTime - startTime) + " ms " + "for " + MATRIX_SIZE + " matrix size");

        startTime = System.currentTimeMillis();
        var ribbonResult = ribbonCalculator.multiplyMatrix(matrix1, matrix2, THREADS_COUNT);
        endTime = System.currentTimeMillis();
        System.out.println("Ribbon result: " + (endTime - startTime) + " ms " + "for " + MATRIX_SIZE + " matrix size");

        startTime = System.currentTimeMillis();
        var foxResult = foxCalculator.multiplyMatrix();
        endTime = System.currentTimeMillis();
        System.out.println("Fox result: " + (endTime - startTime) + " ms " + "for " + MATRIX_SIZE + " matrix size");

        validateCalculationResults(sequentialResult, ribbonResult, foxResult);
    }

    private static void validateCalculationResults(Matrix sequentialResult, Matrix ribbonResult, Matrix foxResult) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (sequentialResult.get(i, j) != ribbonResult.get(i, j) || sequentialResult.get(i, j) != foxResult.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
