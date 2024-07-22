import Calculators.Fox.FoxCalculator;
import Calculators.RibbonCalculator;
import Calculators.SequentialCalculator;
import Matrix.Matrix;
import Matrix.MatrixGenerator;

public class Main {
    final static int MATRIX_SIZE = 8;
    final static int THREADS_COUNT = 4;

    public static void main(String[] args) {
        var matrixGenerator = new MatrixGenerator();
        var matrix1 = matrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
        var matrix2 = matrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

        System.out.println("Matrix 1:");
        matrix1.print2D();
        System.out.println();

        System.out.println("Matrix 2:");
        matrix2.print2D();
        System.out.println();

        var sequentialCalculator = new SequentialCalculator();
        var result = sequentialCalculator.multiplyMatrix(matrix1, matrix2);
        System.out.println("Sequential result:");
        result.print2D();
        System.out.println();

        var foxCalculator = new FoxCalculator(matrix1, matrix2, THREADS_COUNT);
        var result2 = foxCalculator.multiplyMatrix();
        System.out.println("Fox result:");
        result2.print2D();
        System.out.println();

        var ribbonCalculator = new RibbonCalculator();
        var result3 = ribbonCalculator.multiplyMatrix(matrix1, matrix2, THREADS_COUNT);
        System.out.println("Ribbon result:");
        result3.print2D();

        validateCalculationResults(result3, result2);
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
